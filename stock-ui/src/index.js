import React from "react";
import { createRoot } from "react-dom/client";
import { createStore } from "redux";
import { Provider } from "react-redux";
import { composeWithDevTools } from "redux-devtools-extension";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import rootReducer from "./store/reducers/rootReducer";

// Sweet Alert 2
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";

const MySwal = withReactContent(Swal);
// If apps need MySwal, import from a dedicated module; avoid exporting from entrypoint.

const store = createStore(rootReducer, composeWithDevTools());

const root = createRoot(document.getElementById("root"));
root.render(
  <Provider store={store}>
    <App />
  </Provider>
);

serviceWorker.unregister();

// Export MySwal for use in the app (keeps compatibility with existing imports)
export default MySwal;
