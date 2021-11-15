import Landing from "./Landing";
import {CoinPrice} from "./index";
import React from "react";
import {styled} from "@mui/system";
import Recommendation from "./Recommendation";

const StyledContainer = styled('div')`
  min-height: 100vh;
  background: #757ce8;
  background-repeat: no-repeat;
  background-size: cover;
`

const HomePage = () => {
    return (
        <StyledContainer>
            <Landing/>
            <CoinPrice/>
            <Recommendation/>
        </StyledContainer>
    )
}

export default HomePage
