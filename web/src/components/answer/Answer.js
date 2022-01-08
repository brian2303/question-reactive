import React from 'react'
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';

import './answer.css'
import { connect } from 'react-redux';


const Answer = ({ answer,userId }) => {
  const handleClick = () => {
    console.log('click');
  }
  return (
    <aside className='answer'>
      <p className='answer-content'>{answer.answer}</p>
      {userId &&
        <div className='score-button'>
          <KeyboardArrowUpIcon className='keyboard' onClick={handleClick}/>
          <KeyboardArrowDownIcon className='keyboard' onClick={handleClick}/>
        </div>}
    </aside>
  )
}

const mapStateToProps = state => ({
  userId: state.auth.uid
})

export default connect(mapStateToProps)(Answer)