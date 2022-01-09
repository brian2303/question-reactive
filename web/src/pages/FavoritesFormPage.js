import React, { useEffect } from "react";
import { connect } from 'react-redux'

const FavoritesFormPage = ({ dispatch, loading }) => {

}



const mapStateToProps = state => ({
    loading: state.question.loading,
})

export default connect(mapStateToProps)(FavoritesFormPage)