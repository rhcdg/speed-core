// jest-dom adds custom jest matchers for asserting on DOM nodes.
// allows you to do things like:
// expect(element).toHaveTextContent(/react/i)
// learn more: https://github.com/testing-library/jest-dom
import '@testing-library/jest-dom';

const localStorageMock = (function () {
  let store = {};

  return {
    getItem(key: string | number) {
      return store[key] || null;
    },
    setItem(key: string | number, value: { toString: () => any }) {
      store[key] = value.toString();
    },
    removeItem(key: string | number) {
      delete store[key];
    },
    clear() {
      store = {};
    }
  };
})();

Object.defineProperty(window, 'localStorage', {
  value: localStorageMock
});
