import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  getStocks,
  getCreatedStock,
  getUpdatedStock
} from "./app/api";

// Styles
import "./app.scss";

// Components
import Header from "./components/Header";
import Footer from "./components/Footer";
import DataTable from "./components/DataTable";
import CreateStock from "./components/CreateStock";
import UpdateStock from "./components/UpdateStock";
import Modal from "./components/Modal";
import Loader from "./components/Loader";
import MySwal from "./index";

function App() {
  const dispatch = useDispatch();
  const stocks = useSelector(state => state.stocks);

  const [loading, setLoading] = useState(false);

  const [currentStock, setCurrentStock] = useState({
    id: null,
    name: "",
    currentPrice: "",
    lastUpdate: ""
  });
  const [activeModal, setActiveModal] = useState({ name: "", active: false });
  const [savedStocks, setSavedStocks] = useState(stocks);
  const [pageSize] = useState(20);
  const [currentPage] = useState(1);
  const [sorted, setSorted] = useState(false);

  const stocksLastIndex = currentPage * pageSize;
  const stocksFirstIndex = stocksLastIndex - pageSize;
  const currentStocks = stocks.slice(stocksFirstIndex, stocksLastIndex);
  // const currentStocks = savedStocks;


  // Setting up Modal
  const setModal = modal => {

    setActiveModal({ name: modal, active: true });
  };




  // Sorting
  const sorting = key => {
    setSorted(!sorted);
    switch (key) {
      case "name":
        const nameSort = [...savedStocks].sort((a, b) => {
          return sorted
            ? a.name.localeCompare(b.name, "tr")
            : b.name.localeCompare(a.name, "tr");
        });
        dispatch({ type: "SET_STOCKS", data: nameSort });
        return;

      default:
        break;
    }
  };

  // Create Stock
  const createStock = async stock => {
    setActiveModal(false);
    setLoading(true);

    try {
      await getCreatedStock(stock).then(res => {
        const result = res.data;
        MySwal.fire({
          icon: "success",
          title: "Stock created successfully."
        }).then(() => {
          dispatch({ type: "CREATE_STOCK", data: result });
          setSavedStocks([...stocks, result]);
        });
      });
    } catch (err) {
      MySwal.fire({
        icon: "error",
        title: "Failed to create stock."
      });
    } finally {
      setLoading(false);
    }
  };

  // Update Stock
  const updateRow = stock => {
    setModal("Update Stock");

    setCurrentStock({
      id: stock.id,
      name: stock.name,
      currentPrice: stock.currentPrice,
      lastUpdate: stock.lastUpdate
    });
  };

  const updateStock = async (id, updatedStock) => {
    setActiveModal(false);
    setLoading(true);

    try {
      await getUpdatedStock(id, updatedStock).then(res => {
        const result = res.data;
        MySwal.fire({
          icon: "success",
          title: "Stock updated successfully."
        }).then(() => {
          dispatch({
            type: "SET_STOCKS",
            data: stocks.map(stock =>
              stock.id === id ? Object.assign(stock, result) : stock
            )
          });
        });
      });
    } catch (err) {
      MySwal.fire({
        icon: "error",
        title: "Failed to update stock."
      });
    } finally {
      setLoading(false);
    }
  };


  // Fetch Stocks
  const fetchStocks = async () => {
    setLoading(true);

    try {
      await getStocks().then(({ data }) => {
        setSavedStocks(data);
        dispatch({ type: "SET_STOCKS", data: data });
      });
    } catch (err) {
      MySwal.fire({
        icon: "error",
        title: "Failed to fetch stocks."
      });
    } finally {
      setTimeout(() => {
        setLoading(false);
      }, 500);
    }
  };

  useEffect(() => {
    fetchStocks();
  }, []);

  return (
    <div className="app">
      <Header />
      <main className="content">
        <div className="container">
          {loading ? (
            <Loader />
          ) : (
            <div className="content-wrapper">
              <div className="toolbar">
                {/*<Search search={search} resetSearch={search} />*/}
                <button
                  className="primary-btn"
                  onClick={() => setModal("Create Stock")}
                >
                  Create New Stock
                </button>
              </div>
              <DataTable
                stocks={currentStocks}
                updateRow={updateRow}
                onSortChange={sorting}
              />

            </div>
          )}
        </div>
      </main>
      {activeModal.active && (
        <Modal activeModal={activeModal}>
          {activeModal.name === "Create Stock" && (
            <CreateStock
              createStock={createStock}
              setActiveModal={setActiveModal}
            />
          )}
          {activeModal.name === "Update Stock" && (
            <UpdateStock
              currentStock={currentStock}
              updateStock={updateStock}
              setActiveModal={setActiveModal}
            />
          )}

        </Modal>
      )}
      <Footer />
    </div>
  );
}

export default App;
