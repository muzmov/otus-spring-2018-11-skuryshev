import React, {Component} from 'react'

class Authors extends Component {

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
        {props.list.map(it =>
            <Row entity={it} clickHandler={() => props.selectHandler(it.id)}/>
        )}
        </tbody>
    </table>
)

const Row = (props) => {
    return (
        <tr onClick={props.clickHandler}>
            <th scope="row">{props.entity.id}</th>
            <td>{props.entity.firstName}</td>
            <td>{props.entity.lastName}</td>
            <td>{props.entity.middleName}</td>
        </tr>
    )
}

class Card extends Component {

    constructor(props) {
        super(props)
        if (props.entity) {
            this.state = {
                id: props.entity.id,
                firstName: props.entity.firstName,
                lastName: props.entity.lastName,
                middleName: props.entity.middleName
            }
        } else {
            this.state = {
                id: "",
                firstName: "",
                lastName: "",
                middleName: ""
            }
        }
    }

    componentWillReceiveProps(props) {
        if (props.entity) {
            this.setState({
                id: props.entity.id,
                firstName: props.entity.firstName,
                lastName: props.entity.lastName,
                middleName: props.entity.middleName
            })
        } else {
            this.setState({
                id: "",
                firstName: "",
                lastName: "",
                middleName: ""
            })
        }
    }

    handleChange(field, event) {
        const stateChange = {}
        stateChange[field] = event.target.value
        this.setState(stateChange)
    }

    handleSubmit() {
        fetch('/author', {
            method: 'post',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.state)
        }).then(res => this.props.refreshHandler());
    }

    handleDelete() {
        fetch('/author/' + this.state.id, {
            method: 'delete',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
        }).then(res => this.props.refreshHandler());
    }

    render() {
        let title = "New author"
        let deleteButton = null
        if (this.state.id) {
            title = "Edit author"
            deleteButton = <button className="btn btn-primary" onClick={this.handleDelete.bind(this)}>Delete</button>
        }
        return (
            <div className="card">
                <div className="card-body">
                    <form>
                        <h3>{title}:</h3>
                        <div className="form-group">
                            <label htmlFor="InputId1">ID</label>
                            <input type="text" className="form-control" id="InputId1" placeholder="Id"
                                   readOnly="readOnly"
                                   value={this.state.id}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="InputFirstName1">First name</label>
                            <input type="text" className="form-control" id="InputFirstName1" placeholder="First name"
                                   value={this.state.firstName} onChange={e => this.handleChange("firstName", e)}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="InputLastName1">Last name</label>
                            <input type="text" className="form-control" id="InputLastName1" placeholder="Last name"
                                   value={this.state.lastName} onChange={e => this.handleChange("lastName", e)}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="InputMiddleName1">Middle name</label>
                            <input type="text" className="form-control" id="InputMiddleName1" placeholder="Middle name"
                                   value={this.state.middleName} onChange={e => this.handleChange("middleName", e)}/>
                        </div>
                        <button type="submit" className="btn btn-primary mr-2"
                                onClick={this.handleSubmit.bind(this)}>Save</button>
                        {deleteButton}
                    </form>
                </div>
            </div>
        )
    }
}

export default Authors