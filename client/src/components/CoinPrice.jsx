import React, {useContext, useEffect} from "react";
import {GlobalStoreContext} from "../store";
import {useHistory} from "react-router-dom";
import useWindowPosition from "../hook/useWindowPosition";
import ImageCard from "./ImageCard";
import {styled} from "@mui/system";

const StyledContainer = styled('div')`
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`

const CoinPrice = () => {
    const checked = useWindowPosition("landing");

    const {store} = useContext(GlobalStoreContext)
    store.history = useHistory();

    useEffect(() => {
        store.onLoadExchanges();
    }, []);


    function getBuySellPrice() {
        // call with no parameters
        store.getBuySellPrice("kraken", "BTC")
    }

    function loadExchangeOnLoad() {
        store.onLoadExchanges();
    }

    let buyPrice = "";
    if (store) {
        buyPrice = store.loadedExchanges.map((data, idx) => (
            <ImageCard key={idx} checked={checked} data={data} cardIdx={idx}/>
        ))
    }

    return (

        <StyledContainer id="place-to-visit">
            {buyPrice}
        </StyledContainer>
    )
}

export default CoinPrice;