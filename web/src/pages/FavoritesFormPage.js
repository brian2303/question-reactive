import React, { useEffect } from "react";
import { connect } from 'react-redux'
import { getFavoritesUser } from "../actions/questionActions";

const FavoritesFormPage = ({ dispatch,favorites, userId }) => {

    useEffect(() => {
        dispatch(getFavoritesUser(userId))
    }, [dispatch,userId])

    return (
        <section>
            {favorites.map(favorite => (
                <div>
                    <p>{favorite.question}</p>
                    <button>DELETE</button>
                </div>
            ))}
        </section>

    );
}

const mapStateToProps = state => ({
    userId: state.auth.uid,
    favorites : state.question.favorites
})

export default connect(mapStateToProps)(FavoritesFormPage)