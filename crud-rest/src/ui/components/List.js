import React from 'react'
import Row from './Row'

const List = (props) => (
    <table className="table table-hover">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">First name</th>
            <th scope="col">Last name</th>
            <th scope="col">Middle Name</th>
        </tr>
        </thead>
        <tbody>
        {props.list.map((it, index) =>
            <Row entity={it} key={index} clickHandler={() => props.selectHandler(it.id)}/>
        )}
        </tbody>
    </table>
)

export default List