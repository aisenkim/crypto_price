import React, {useContext, useEffect, useState} from "react";
import {GlobalStoreContext} from "../store";
import {useHistory} from "react-router-dom";
import {styled} from "@mui/system";
import {makeStyles} from "@mui/styles";
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Button from "@mui/material/Button";
import api from "../api";

const StyledContainer = styled('div')`
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`

const ButtonDiv = styled('div')`
  margin-top: 7vh;
`;

const useStyles = makeStyles({
    appbar: {
        background: "none",
    },
    appbarTitle: {
        flexGrow: "1",
    },
    appbarWrapper: {
        width: "80%",
        margin: "0 auto",
    },
    icon: {
        color: "#fff",
        fontSize: "2rem",
    },
    colorText: {
        color: "#f8f8f2",
    },
    title: {
        color: "#5AFF3D",
        fontSize: "4.5rem",
    },
    moneyText : {
     fontSize: "2.5rem"
    },
    container: {
        textAlign: "center",
    },
    goDown: {
        color: "#5AFF3D",
        fontSize: ["4rem", "!important"],
    },
});

const Recommendation = () => {
    const classes = useStyles();
    const [buttonClicked, setButtonClicked] = React.useState(false);
    const [buyRecommend, setBuyRecommend] = React.useState(null);
    const [sellRecommend, setSellRecommend] = React.useState(null);

    const [exchangeOne, setExchangeOne] = React.useState('');
    const handleChangeExchangeOne = (event) => {
        setExchangeOne(event.target.value);
    };

    const [exchangeTwo, setExchangeTwo] = React.useState('');
    const handleChangeExchangeTwo = (event) => {
        setExchangeTwo(event.target.value);
    };

    const [cryptocurrency, setCryptoCurrency] = React.useState('');
    const handleChangeCrypto = (event) => {
        setCryptoCurrency(event.target.value);
    };

    const {store} = useContext(GlobalStoreContext)
    store.history = useHistory();


    async function getRecommendation() {
        const firstExchange = await api.getBuySellPrice(exchangeOne, cryptocurrency);
        const secondExchange = await api.getBuySellPrice(exchangeTwo, cryptocurrency);

        const firstBuyPrice = parseFloat(firstExchange.data.buyPrice.replace(/,/g, ''))
        const secondBuyPrice = parseFloat(secondExchange.data.buyPrice.replace(/,/g, ''))
        const firstSellPrice = parseFloat(firstExchange.data.sellPrice.replace(/,/g, ''))
        const secondSellPrice = parseFloat(secondExchange.data.sellPrice.replace(/,/g, ''))

        setBuyRecommend(firstBuyPrice < secondBuyPrice ? {
                exchange: exchangeOne,
                price: firstExchange.data.buyPrice
            } :
            {
                exchange: exchangeTwo, price: secondExchange.data.buyPrice
            })
        setSellRecommend(firstSellPrice < secondSellPrice ? {
                exchange: exchangeOne,
                price: firstExchange.data.sellPrice
            } :
            {
                exchange: exchangeTwo, price: secondExchange.data.sellPrice
            })
        setButtonClicked(true)
    }

    return (
        <StyledContainer id="recommendation">
            <div className={classes.container}>
                <h1 className={classes.title}>Compare Cryptocurrency</h1>
                <FormControl variant="standard" sx={{m: 1, minWidth: 120}}>
                    <InputLabel id="demo-simple-select-standard-label">Exchange</InputLabel>
                    <Select
                        labelId="demo-simple-select-standard-label"
                        id="demo-simple-select-standard"
                        value={exchangeOne}
                        onChange={handleChangeExchangeOne}
                        label="ExchangeOne"
                    >
                        <MenuItem value={"kraken"}>Kraken</MenuItem>
                        <MenuItem value={"blockchain"}>Blockchain</MenuItem>
                    </Select>
                </FormControl>
                <FormControl variant="standard" sx={{m: 1, minWidth: 120}}>
                    <InputLabel id="demo-simple-select-standard-label">Exchange</InputLabel>
                    <Select
                        labelId="demo-simple-select-standard-label"
                        id="demo-simple-select-standard"
                        value={exchangeTwo}
                        onChange={handleChangeExchangeTwo}
                        label="ExchangeTwo"
                    >
                        <MenuItem value={"kraken"}>Kraken</MenuItem>
                        <MenuItem value={"blockchain"}>Blockchain</MenuItem>
                    </Select>
                </FormControl>
                <FormControl variant="standard" sx={{m: 1, minWidth: 120}}>
                    <InputLabel id="demo-simple-select-standard-label">Cryptocurrency</InputLabel>
                    <Select
                        labelId="demo-simple-select-standard-label"
                        id="demo-simple-select-standard"
                        value={cryptocurrency}
                        onChange={handleChangeCrypto}
                        label="cryptocurrency"
                    >
                        <MenuItem value={"BTC"}>Bitcoin</MenuItem>
                        <MenuItem value={"ETH"}>Ethereum</MenuItem>
                    </Select>
                </FormControl>
                <ButtonDiv>
                    <Button variant="contained" onClick={getRecommendation}>Get Recommendation</Button>
                </ButtonDiv>
                {buttonClicked ?
                    <div className={classes.container}>
                        <h1 className={classes.moneyText}>Buy from: {buyRecommend.exchange} - <span className={classes.colorText}>${buyRecommend.price}</span></h1>
                        <h1 className={classes.moneyText}>Sell from: {sellRecommend.exchange} - <span className={classes.colorText}>${sellRecommend.price}</span></h1>
                    </div>
                    :
                    null
                }
            </div>

        </StyledContainer>
    )
}

export default Recommendation;