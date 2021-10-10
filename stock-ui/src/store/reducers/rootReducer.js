const initialState = {
  stocks: []
};

const rootReducer = (state = initialState, action) => {
  switch (action.type) {
    case "SET_STOCKS":
      return { ...state, stocks: action.data };
    case "CREATE_STOCK":
      return { ...state, stocks: [...state.stocks, action.data] };
    default:
      return state;
  }
};

export default rootReducer;
