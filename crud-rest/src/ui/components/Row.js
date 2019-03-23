import React from 'react'

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

export default Row