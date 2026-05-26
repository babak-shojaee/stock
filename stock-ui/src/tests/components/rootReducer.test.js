import rootReducer from '../../store/reducers/rootReducer';

const stocks = [
  { id: 1, name: 'AAPL', currentPrice: 150 },
  { id: 2, name: 'GOOG', currentPrice: 200 },
];

test('returns initial state', () => {
  expect(rootReducer(undefined, {})).toEqual({ stocks: [] });
});

test('SET_STOCKS replaces stocks', () => {
  expect(rootReducer(undefined, { type: 'SET_STOCKS', data: stocks })).toEqual({ stocks });
});

test('CREATE_STOCK appends a stock', () => {
  const state = { stocks: [stocks[0]] };
  const result = rootReducer(state, { type: 'CREATE_STOCK', data: stocks[1] });
  expect(result.stocks).toHaveLength(2);
  expect(result.stocks[1]).toEqual(stocks[1]);
});

test('unknown action returns current state', () => {
  const state = { stocks };
  expect(rootReducer(state, { type: 'UNKNOWN' })).toBe(state);
});
