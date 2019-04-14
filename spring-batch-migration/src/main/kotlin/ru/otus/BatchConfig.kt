package ru.otus


import org.hibernate.SessionFactory
import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoOperations
import ru.otus.model.Book
import javax.persistence.EntityManagerFactory


@EnableBatchProcessing
@Configuration
class BatchConfig {
    private val logger = LoggerFactory.getLogger("Batch")

    @Autowired
    lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    lateinit var stepBuilderFactory: StepBuilderFactory

    @Bean
    fun reader(entityManagerFactory: EntityManagerFactory) = HibernateCursorItemReaderBuilder<Book>()
        .name("bookItemReader")
        .queryString("select b from Book b")
        .sessionFactory(entityManagerFactory.unwrap(SessionFactory::class.java))
        .build()

    @Bean
    fun processor(): ItemProcessor<Book, Book> {
        return ItemProcessor { it }
    }

    @Bean
    fun writer(mongoOperations: MongoOperations) = ItemWriter<Book> {
        for (book in it) {
            book.authors.forEach { mongoOperations.save(it) }
            book.genres.forEach { mongoOperations.save(it) }
            mongoOperations.save(book)
        }
    }

    @Bean
    fun importUserJob(step1: Step) = jobBuilderFactory.get("migrationJob")
        .incrementer(RunIdIncrementer())
        .flow(step1)
        .end()
        .listener(object : JobExecutionListener {
            override fun beforeJob(jobExecution: JobExecution) {
                logger.info("Начало job")
            }

            override fun afterJob(jobExecution: JobExecution) {
                logger.info("Конец job")
            }
        })
        .build()

    @Bean
    fun step1(writer: ItemWriter<Book>, reader: ItemReader<Book>, itemProcessor: ItemProcessor<Book, Book>) =
        stepBuilderFactory.get("step1")
            .chunk<Book, Book>(5)
            .reader(reader)
            .processor(itemProcessor)
            .writer(writer)
            .build()
}
