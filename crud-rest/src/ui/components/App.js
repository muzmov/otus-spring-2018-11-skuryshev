import React, {Component} from 'react'
import List from './List'
import Card from './Card'

class App extends Component {
    constructor(props) {
        super(props)
        this.state = {
            list: [],
            selected: null
        }
    }

    componentDidMount() {
        this.refresh()
    }

    refresh() {
        fetch('/authors')
            .then(response => response.json())
            .then(authors => this.setState({list: authors, selected: null}));
    }

    selectHandler(id) {
        this.setState({selected: id})
    }

    render() {
        const selectedEntity = this.state.list.find(it => it.id === this.state.selected)
        return (
            <div className="container">
                <div className="row">
                    <div className="col-sm">
                        <List list={this.state.list} selectHandler={this.selectHandler.bind(this)}/>
                        <button className="btn btn-primary"
                                onClick={() => this.selectHandler(null)}>New</button>
                    </div>
                    <div className="col-sm">
                        <Card entity={selectedEntity} refreshHandler={this.refresh.bind(this)}/>
                    </div>
                </div>
            </div>
        )
    }
}

export default App