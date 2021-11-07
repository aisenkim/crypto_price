import axios from 'axios'
const api = axios.create({
    baseURL: 'http://localhost:8080/api/v1'
})

export const getBuySellPrice = (exchange, symbol) => api.get(`/exchanges?exchange=${exchange}&coinSymbol=${symbol}`)

const apis = {
   getBuySellPrice
}

export default apis