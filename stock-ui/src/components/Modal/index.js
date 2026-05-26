import React from "react";

const Modal = ({ children, activeModal, onClose }) => (
  <div
    className="fixed inset-0 z-50 flex items-center justify-center bg-black/50"
    onClick={onClose}
  >
    <div
      className="bg-white rounded-xl shadow-xl w-full max-w-md mx-4 overflow-hidden"
      onClick={e => e.stopPropagation()}
    >
      <div className="flex items-center justify-between px-6 py-4 border-b border-gray-100">
        <h3 className="text-lg font-semibold text-gray-800">{activeModal.name}</h3>
        <button
          className="text-gray-400 hover:text-gray-600 text-xl leading-none"
          onClick={onClose}
          aria-label="Close"
        >
          ×
        </button>
      </div>
      <div className="px-6 py-5">{children}</div>
    </div>
  </div>
);

export default Modal;
