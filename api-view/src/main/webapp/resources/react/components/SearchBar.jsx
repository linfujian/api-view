import React from 'react';

export default class SearchBar extends React.Component {
  constructor(props) {
    super(props);
  }

  handleChange: function(){
    this.props.onUserInput(
      this.refs.classFilterInput.value,
      this.refs.classFilterInput.value
    );
  }

  render() {
    return (
      <form>
        <input type="text" placeholder="classFilter here..." value={this.props.classFilter} ref="classFilterInput" onChange="this.handleChange" />
        <input type="text" placeholder="vcfAnnoFilter here..." value={this.props.categoryFilter} ref="vcfAnnoFilterInput" onChange="this.handleChange" />
      </form>
    );
  }

}
