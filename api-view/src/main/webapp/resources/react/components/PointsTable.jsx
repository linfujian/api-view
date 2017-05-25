import React from 'react';
import PointRow from './PointRow.jsx';

export default class PointsTable extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    // const rows = [];
    // this.props.points.each((i, point) => {
    //   if(point.class === this.props.classFilter || point.category === this.props.categoryFilter) {
    //     return;
    //   }
    //   rows.push(<PointRow key={i} point={point} />);
    // });

    return (
      <table className="table">
          <thead>
            <tr>
              <td>CHROM</td>
              <td>POS</td>
              <td>REF</td>
              <td>ALT</td>
              <td>CLASS</td>
              <td>Category</td>
            </tr>
          </thead>
          <tbody>
            {this.props.points.map((i, point) => (
              <PointRow key={i} point={point} />
            ))}
          </tbody>
      </table>
    );
  }
}
