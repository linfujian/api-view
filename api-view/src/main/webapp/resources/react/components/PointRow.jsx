import React from 'react';

export default class PointRow  extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <tr>
          <td>{this.props.point.chr}</td>
          <td>{this.props.point.pos}</td>
          <td>{this.props.point.ref}</td>
          <td>{this.props.point.alt}</td>
          <td>{this.props.point.class}</td>
          <td>{this.props.point.category}</td>
        </tr>
    );
  }
}
