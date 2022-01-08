import React from 'react'
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';

import './answer.css'


export const Answer = ({ answer }) => {
  const handleClick = () => {
    console.log('click');
  }
  return (
    <aside className='answer'>
      <p className='answer-content'>{answer.answer}</p>
      <div className='score-button'>
        <KeyboardArrowUpIcon className='keyboard' onClick={handleClick}/>
        <KeyboardArrowDownIcon className='keyboard' onClick={handleClick}/>
      </div>
    </aside>
  )
}
