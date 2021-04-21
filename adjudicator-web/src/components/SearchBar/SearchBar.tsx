import React from 'react';
import { InputBase } from '@material-ui/core';

interface SearchBarProps {
  handleSearch: React.Dispatch<React.SetStateAction<string>>;
}

const SearchBar: React.FC<SearchBarProps> = ({ handleSearch }) => {
  return (
    <InputBase
      className="search-bar"
      placeholder="Search by A-Number or Case Number"
      inputProps={{
        'data-testid': 'search-bar',
        'aria-label': 'Search by A-Number or Case Number'
      }}
      onChange={e => handleSearch(e.currentTarget.value)}
    />
  );
};

export default SearchBar;
