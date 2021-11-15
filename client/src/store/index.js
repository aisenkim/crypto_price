import {createContext, useState} from "react";
import api from "../api";

/**
 * This is our global data store.
 * Using FLUX design pattern
 * @author Aisen Kim
 */

export const GlobalStoreContext = createContext({});

export const GlobalStoreActionType = {
    LOAD_PRICE: "LOAD_PRICE",
    ON_LOAD_EXCHANGES: "ON_LOAD_EXCHANGES",
}

// MAKING IT AVAILABLE TO THE REST OF
// THE APPLICATION
export const useGlobalStore = () => {

    /**
     * buySellPrice -> [{exchange: kraken, coin: BTC, buyPrice: 1300, sellPrice: 1320},
     *                  {exchange: coinbase, coin: ETH, buyPrice: 5000, sellPrice: 5120}]
     */

        // THINGS THAT OUR DATA WILL MANAGE
    const [store, setStore] = useState({
            buySellPrice: [],
            loadedExchanges: [],
            exchangeOne: "kraken",
            exchangeTwo: "blockchain",
            coinSymbolOne: "BTC",
            coinSymbolTwo: "BTC",
        })

    // REDUCER -> HANDLE EVERY TYPE OF STATE CHANGES
    const storeReducer = (action) => {
        const {type, payload} = action;
        switch (type) {
            case GlobalStoreActionType.LOAD_PRICE: {
                return setStore({
                    buySellPrice: payload
                })
            }
            case GlobalStoreActionType.ON_LOAD_EXCHANGES: {
                return setStore({
                    loadedExchanges: payload.loadedExchanges,
                    exchangeOne: payload.exchangeOne,
                    exchangeTwo: payload.exchangeTwo,
                    coinSymbolOne: payload.coinSymbolOne,
                    coinSymbolTwo: payload.coinSymbolTwo
                })
            }
            default:
                return store;
        }
    }

    // THESE ARE THE FUNCTIONS THAT WILL UPDATE OUR STORE AND
    // DRIVE THE STATE OF THE APPLICATION. WE'LL CALL THESE IN
    // RESPONSE TO EVENTS INSIDE OUR COMPONENTS.

    // GET COIN PRICE FROM THE SERVER
    store.getBuySellPrice = function (exchange, coinSymbol) {
        async function asyncGetBuySellPrice(exchange, coinSymbol) {
            const response = await api.getBuySellPrice(exchange, coinSymbol);
            if (response.status === 200 && response.data) {
                const payload = {
                    "exchange": exchange,
                    "coin": coinSymbol,
                    "buyPrice": response.data.buyPrice,
                    "sellPrice": response.data.sellPrice
                }
                const buySellPrice = [payload]
                storeReducer({
                    type: GlobalStoreActionType.LOAD_PRICE,
                    payload: buySellPrice
                })
            } else {
                console.log("API FAILED TO GET BUY SELL PRICE")
            }
        }

        asyncGetBuySellPrice(exchange, coinSymbol);
    }

    // Load default coin price of two exchanges on landing page
    store.onLoadExchanges = function (exchangeOne = "kraken", coinSymbolOne = "BTC", exchangeTwo = "blockchain", coinSymbolTwo = "BTC") {
        async function asyncOnLoadExchange() {
            try {
                const firstExchange = await api.getBuySellPrice(exchangeOne, coinSymbolOne);
                const secondExchange = await api.getBuySellPrice(exchangeTwo, coinSymbolTwo);


                const firstExchangePayload = {
                    "exchange": exchangeOne,
                    "coin": coinSymbolOne,
                    "buyPrice": firstExchange.data.buyPrice,
                    "sellPrice": firstExchange.data.sellPrice
                }
                const secondExchangePayload = {
                    "exchange": exchangeTwo,
                    "coin": coinSymbolTwo,
                    "buyPrice": secondExchange.data.buyPrice,
                    "sellPrice": secondExchange.data.sellPrice
                }
                storeReducer({
                    type: GlobalStoreActionType.ON_LOAD_EXCHANGES,
                    payload: {
                        loadedExchanges: [firstExchangePayload, secondExchangePayload],
                        exchangeOne,
                        exchangeTwo,
                        coinSymbolOne,
                        coinSymbolTwo
                    }
                })
            } catch (err) {
                console.log(err);
            }
        }

        asyncOnLoadExchange();
    }

    // THIS GIVES OUR STORE AND ITS REDUCER TO ANY COMPONENT THAT NEEDS IT
    return {store, storeReducer}

}



