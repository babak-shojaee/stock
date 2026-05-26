import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { getStocks, getCreatedStock, getUpdatedStock } from "./app/api";

import Header from "./components/Header";
import DataTable from "./components/DataTable";
import CreateStock from "./components/CreateStock";
import UpdateStock from "./components/UpdateStock";
import Modal from "./components/Modal";
import Loader from "./components/Loader";
import MySwal from "./utils/swal";

const EMPTY_STOCK = { id: null, name: "", currentPrice: "", lastUpdate: "" };

function App() {
  const dispatch = useDispatch();
  const stocks = useSelector(state => state.stocks);

  const [loading, setLoading] = useState(false);
  const [currentStock, setCurrentStock] = useState(EMPTY_STOCK);
  const [activeModal, setActiveModal] = useState({ name: "", active: false });
  const [sorted, setSorted] = useState(false);

  const fetchStocks = async () => {
    setLoading(true);
    try {
      const { data } = await getStocks();
      dispatch({ type: "SET_STOCKS", data });
    } catch {
      MySwal.fire({ icon: "error", title: "Failed to fetch stocks." });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchStocks();
  }, []); // eslint-disable-line react-hooks/exhaustive-deps

  const openModal = name => setActiveModal({ name, active: true });
  const closeModal = () => setActiveModal({ name: "", active: false });

  const sorting = key => {
    const dir = sorted ? 1 : -1;
    const sorted_stocks = [...stocks].sort((a, b) =>
      key === "name" ? dir * a.name.localeCompare(b.name, "tr") : 0
    );
    setSorted(!sorted);
    dispatch({ type: "SET_STOCKS", data: sorted_stocks });
  };

  const createStock = async stock => {
    try {
      const { data: result } = await getCreatedStock(stock);
      closeModal();
      await MySwal.fire({ icon: "success", title: "Stock created successfully." });
      dispatch({ type: "CREATE_STOCK", data: result });
    } catch (err) {
      const msg = err?.response?.data?.message || "Failed to create stock.";
      throw new Error(msg);
    }
  };

  const openUpdateModal = stock => {
    setCurrentStock(stock);
    openModal("Update Stock");
  };

  const updateStock = async (id, updatedStock) => {
    try {
      const { data: result } = await getUpdatedStock(id, updatedStock);
      closeModal();
      await MySwal.fire({ icon: "success", title: "Stock updated successfully." });
      dispatch({
        type: "SET_STOCKS",
        data: stocks.map(s => (s.id === id ? { ...s, ...result } : s))
      });
    } catch (err) {
      const msg = err?.response?.data?.message || "Failed to update stock.";
      throw new Error(msg);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <Header />
      <main className="flex-1 py-10">
        <div className="max-w-5xl mx-auto px-4">
          {loading ? (
            <Loader />
          ) : (
            <>
              <div className="flex justify-between items-center mb-6">
                <h2 className="text-xl font-semibold text-gray-700">Stock List</h2>
                <button
                  className="bg-indigo-600 hover:bg-indigo-700 text-white font-semibold px-4 py-2 rounded-lg transition-colors"
                  onClick={() => openModal("Create Stock")}
                >
                  + New Stock
                </button>
              </div>
              <DataTable stocks={stocks} updateRow={openUpdateModal} onSortChange={sorting} />
            </>
          )}
        </div>
      </main>
      {activeModal.active && (
        <Modal activeModal={activeModal} onClose={closeModal}>
          {activeModal.name === "Create Stock" && (
            <CreateStock createStock={createStock} setActiveModal={setActiveModal} />
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
    </div>
  );
}

export default App;
