import React, { useEffect, useState } from "react";

const DURATION = 5000;

const UndoToast = ({ message, onUndo, onDismiss }) => {
  const [progress, setProgress] = useState(100);

  useEffect(() => {
    const start = Date.now();
    const interval = setInterval(() => {
      const elapsed = Date.now() - start;
      const remaining = Math.max(0, 100 - (elapsed / DURATION) * 100);
      setProgress(remaining);
      if (remaining === 0) clearInterval(interval);
    }, 50);
    const timer = setTimeout(onDismiss, DURATION);
    return () => { clearInterval(interval); clearTimeout(timer); };
  }, [onDismiss]);

  return (
    <div className="fixed bottom-6 left-1/2 -translate-x-1/2 z-50 bg-gray-800 text-white rounded-xl shadow-xl overflow-hidden min-w-72">
      <div className="flex items-center justify-between px-4 py-3 gap-4">
        <span className="text-sm">{message}</span>
        <button
          onClick={onUndo}
          className="text-indigo-300 hover:text-indigo-100 text-sm font-semibold whitespace-nowrap"
        >
          Undo
        </button>
      </div>
      <div
        className="h-1 bg-indigo-500 transition-all"
        style={{ width: `${progress}%` }}
      />
    </div>
  );
};

export default UndoToast;
