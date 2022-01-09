import React from 'react'
import ArrowDropUpTwoToneIcon from '@mui/icons-material/ArrowDropUpTwoTone';
import ArrowDropDownTwoToneIcon from '@mui/icons-material/ArrowDropDownTwoTone';
import { connect } from 'react-redux';
import { postUpdatePositionAnswer } from '../../actions/answerActions';

import './answer.css'

const Answer = ({ answer,dispatch,userId }) => {

  
  const handleClick = (action) => {
    const data = {
      userId : answer.userId,
      answerId : answer.answerId,
      questionId: answer.questionId,
      action
    }
    dispatch(postUpdatePositionAnswer(data))
  }
  
  
  return (
    <aside className='answer'>
      <div className='container-answer'>
        {userId === answer.userId && <button className="button right btn-w">Eliminar</button>}
        <p className='answer-content'>{answer.answer}</p>
      </div>
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