import React from 'react'
import ArrowDropUpTwoToneIcon from '@mui/icons-material/ArrowDropUpTwoTone';
import ArrowDropDownTwoToneIcon from '@mui/icons-material/ArrowDropDownTwoTone';
import { connect } from 'react-redux';
import { deleteAnswer, postUpdatePositionAnswer } from '../../actions/answerActions';
import swal from 'sweetalert';

import './answer.css'

const Answer = ({ answer,dispatch,userId }) => {

  const handleDelete = (AnswerId,questionId) => {
    swal({
        title: "Estas seguro de eliminar?",
        text: "Una vez eliminada no podras recuperar esta pregunta",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      })
      .then((willDelete) => {
        if (willDelete) {
            dispatch(deleteAnswer(AnswerId,questionId))
            swal("Tu respuesta ha sido eliminada exitosamente", {
                icon: "success",
            });
        }
      });
    }
  
  const handleClick = (action) => {
    const data = {
      userId,
      answerId : answer.answerId,
      questionId: answer.questionId,
      action
    }
    dispatch(postUpdatePositionAnswer(data))
  }
  
  
  return (
    <aside className='answer'>
      <div className='container-answer'>
        {userId === answer.userId && 
          <button onClick={() => handleDelete(answer.answerId,answer.questionId)} className="button right btn-w">Eliminar</button>}
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