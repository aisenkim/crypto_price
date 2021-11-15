import React, {useContext, useEffect, useState} from "react";
import {GlobalStoreContext} from "../store";
import {useHistory} from "react-router-dom";
import useWindowPosition from "../hook/useWindowPosition";
import ImageCard from "./ImageCard";
import {styled} from "@mui/system";
import {Grid, IconButton} from "@mui/material";
import {makeStyles} from "@mui/styles";
import {Collapse} from "@material-ui/core";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import {Link as Scroll} from "react-scroll";

const StyledContainer = styled('div')`
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`

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
        color: "#5AFF3D",
    },
    title: {
        color: "#fff",
        fontSize: "4.5rem",
    },
    container: {
        textAlign: "center",
    },
    goDown: {
        color: "#5AFF3D",
        fontSize: ["4rem", "!important"],
    },
});

const CoinPrice = () => {
    const classes = useStyles();
    const checked = useWindowPosition("landing");
    const [isChecked, setIsChecked] = useState(false);
    const {store} = useContext(GlobalStoreContext)
    store.history = useHistory();

    useEffect(() => {
        store.onLoadExchanges();
        setIsChecked(true);
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
            <Grid key={idx} item xs={12} sm={6}>
                <ImageCard key={idx} checked={checked} data={data} cardIdx={idx}/>
            </Grid>
        ))
    }

    return (
        <StyledContainer id="coin-price">
            {/*<Collapse*/}
            {/*    in={checked}*/}
            {/*    {...(checked ? {timeout: 1000} : {})}*/}
            {/*    collapsedSize={50}*/}
            {/*>*/}
                <div className={classes.container}>
                    <Grid container>
                        {buyPrice}
                    </Grid>
                    <Scroll to="recommendation" smooth={true}>
                        <IconButton>
                            <ExpandMoreIcon className={classes.goDown}/>
                        </IconButton>
                    </Scroll>
                </div>
            {/*</Collapse>*/}
        </StyledContainer>
    )
}

export default CoinPrice;