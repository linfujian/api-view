import React from 'react';
import axios from 'axios';
import PointsTable from './PointsTable.jsx';

export default class Query extends React.Component {
  constructor(props) {
    super(props);
    this.state = { points: [] };
    this.sendAxios = this.sendAxios.bind(this);
  }


  sendAxios() {
    axios({
      method:'get',
      url:'/queryallpoints/' + document.getElementById('SampleID'),
      responseType:'json'
    })
      .then(response => {
        if (response.status === 200) {
          this.setState({ points: response.data });
        }
      });
  }

  render() {
    return (
      <form className="form-inline">
        <div className="form-group">
          <label>SampleID:</label>
          <input className="form-control" id="SampleID" type="text" placeholder="pl input SampleID..." />
          <button type="button" className="btn btn-default" onClick={this.sendAxios}>Query</button>
        </div>
        <div>
          <PointsTable points={this.state.points} />
        </div>
      </form>

    );
  }
}
