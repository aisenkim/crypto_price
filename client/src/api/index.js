import axios from "axios";
const api = axios.create({
  // baseURL: 'http://localhost:8080/api/v1'
  baseURL: "http://localhost:9000/api/v1", // for deployment
});

export const getBuySellPrice = (exchange, symbol) =>
  api.get(`/exchanges?exchange=${exchange}&coinSymbol=${symbol}`);

const apis = {
  getBuySellPrice,
};

export default apis;
