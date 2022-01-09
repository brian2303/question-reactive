import React from 'react'
import ArrowDropUpTwoToneIcon from '@mui/icons-material/ArrowDropUpTwoTone';
import ArrowDropDownTwoToneIcon from '@mui/icons-material/ArrowDropDownTwoTone';

import './answer.css'
import { connect } from 'react-redux';
import { postUpdatePostionAnswer } from '../../actions/questionActions';


const Answer = ({ answer,dispatch,userId }) => {
  
  const handleClick = (action) => {
    console.log(answer)
    const data = {
      userId : answer.userId,
      answerId : answer.answerId,
      questionId: answer.questionId,
      action
    }
    dispatch(postUpdatePostionAnswer(data))
  }
  
  
  return (
    <aside className='answer'>
      <p className='answer-content'>{answer.answer}</p>
      {userId &&
        <div className='score-button'>
          <ArrowDropUpTwoToneIcon className='keyboard' onClick={() => handleClick("sum")}/>
          <span>{answer.position}</span>
          <ArrowDropDownTwoToneIcon className='keyboard' onClick={() => handleClick("rest")}/>
        </div>}
    </aside>
  )
}

const mapStateToProps = state => ({
  userId: state.auth.uid
})

export default connect(mapStateToProps)(Answer)