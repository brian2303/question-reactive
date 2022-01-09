import React from 'react'
import { Link } from 'react-router-dom'
import StarIcon from '@mui/icons-material/Star';
import StarOutlineIcon from '@mui/icons-material/StarOutline';
import './question.css'

export const Question = ({ question, excerpt, onDelete }) => {

  const handleClick = () => {

  }

  return (
    <article className={excerpt ? 'question-excerpt' : 'question'}>
      <div className='container-question-title'>
        <h2>{question.question}</h2>
        {question.starred && <div className='container-favorites' onClick={handleClick}>
          <p className='text-favorite'><b>star</b></p>
          <StarOutlineIcon />
        </div>}
      </div>
      <p>{question.category}  - <small>{question.type}</small></p>

      {onDelete && (
        <button className="button right" onClick={() => onDelete(question.id)}>DELETE</button>
      )}
      {excerpt && (
        <Link to={`/question/${question.id}`} className="button">
          View Question
        </Link>
      )}
    </article>
  )
}

