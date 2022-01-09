import { combineReducers } from 'redux'
import questionsReducer from './questionsReducer';
import answerReducer from './answerReducer';
import authReducer from './authReducer';

const rootReducer = combineReducers({
    question: questionsReducer,
    answer: answerReducer,
    auth: authReducer
})

export default rootReducer
