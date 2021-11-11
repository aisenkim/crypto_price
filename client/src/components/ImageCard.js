import React, {useContext} from "react";
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import {Collapse, ToggleButton, ToggleButtonGroup} from '@mui/material'
import {styled} from "@mui/system";
import {GlobalStoreContext} from "../store";

const CardStyled = styled(Card)`
  //max-width: 645px;
  background: rgba(0, 0, 0, 0.5);
  margin: 3vw;
`

const ButtonGroupStyle = styled(ToggleButtonGroup)`
  margin-bottom: 0.5vh;
`

const ExchangeName = styled(Typography)`
  font-family: Nunito, sans-serif;
  font-weight: bold;
  font-size: 2rem;
  color: #5AFF3D;
`

const PriceTypography = styled(Typography)`
  font-family: Nunito, sans-serif;
  font-weight: bold;
  font-size: 1.5rem;
  color: #fff;
`


export default function ImageCard(props) {
    const [alignment, setAlignment] = React.useState('BTC');
    const {store} = useContext(GlobalStoreContext)

    const handleChange = (event, newAlignment) => {
        setAlignment(newAlignment);
        // check the value and call the api to get value
        if (props.cardIdx === 0) {
            store.onLoadExchanges(props.data.exchange, event.target.value, store.exchangeTwo, store.coinSymbolTwo);
        } else {
            store.onLoadExchanges(store.exchangeOne, store.coinSymbolOne, props.data.exchange, event.target.value);
        }
    };


    return (
        <Collapse in={props.checked} {...(props.checked ? {timeout: 1000} : {})}>
            <CardStyled>
                <CardContent>
                    <ExchangeName
                        gutterBottom
                        variant="h5"
                        component="h2"
                    >
                        {props.data.exchange}
                    </ExchangeName>
                    <ButtonGroupStyle
                        color="primary"
                        value={alignment}
                        exclusive
                        onChange={handleChange}
                    >
                        <ToggleButton value="BTC">Bitcoin</ToggleButton>
                        <ToggleButton value="ETH">Ethereum</ToggleButton>
                    </ButtonGroupStyle>
                    <PriceTypography
                        variant="body2"
                        color="textSecondary"
                        component="p"
                    >
                        Buy Price: {props.data.buyPrice}<br/>
                        Sell Price : {props.data.sellPrice}
                        <br/>
                        {/*Lizards are a widespread group of squamate reptiles, with over 6,000*/}
                        {/*species, ranging across all continents except Antarctica*/}
                    </PriceTypography>
                </CardContent>
                <CardActions>
                    <Button size="small" color="primary">
                        Share
                    </Button>
                    <Button size="small" color="primary">
                        Learn More
                    </Button>
                </CardActions>
            </CardStyled>
        </Collapse>
    );
}
