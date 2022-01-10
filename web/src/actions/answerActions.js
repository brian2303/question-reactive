const URL_BASE = 'http://localhost:8080';

export const LOADING = 'LOADING'
export const LOADED_SUCCESS = 'LOADED_SUCCESS'
export const LOADED_FAILURE = 'LOADED_FAILURE'

export const loading = () => ({ type: LOADING })

export const success = payload => ({
    type: LOADED_SUCCESS,
    payload
});

export const failure = () => ({ type: LOADED_FAILURE })

export function postAnswer(answer) {
    return async dispatch => {
        dispatch(loading())
        try {
            await fetch(`${URL_BASE}/add/${answer.userId}`,
                {
                    method: 'POST',
                    mode: 'cors',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(answer)
                }
            )
            dispatch(success({redirect: `/question/${answer.questionId}`}));
        } catch (error) {
            dispatch(failure())
        }
    }
}

export function postUpdatePositionAnswer(updatePosition) {
    return async dispatch => {
        dispatch(loading())
        try{
            const response = await fetch(`${URL_BASE}/updatePosition`,
                {
                    method: 'POST',
                    mode: 'cors',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(updatePosition)
                }
            )
            const answerPositionUser = await response.json();
            dispatch(success({redirect: `/question/${answerPositionUser.questionId}`}));
        }catch (error) {
            dispatch(failure())
        }
    }
}

export function deleteAnswer(AnswerId,questionId) {
    return async dispatch => {
        dispatch(loading())
        try{
            await fetch(`${URL_BASE}/deleteAnswer/${AnswerId}`,
                {
                    method: 'DELETE',
                    mode: 'cors',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            )
            dispatch(success({redirect: `/question/${questionId}`}));
        }catch (error) {
            dispatch(failure())
        }
    }
}