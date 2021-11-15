import {Collapse, IconButton} from "@mui/material";
import {makeStyles} from '@mui/styles';
import React, {useEffect, useState} from "react";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import {Link as Scroll} from "react-scroll";
import {styled} from "@mui/system";

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

const StyledContainer = styled('div')`
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: Nunito, sans-serif;
`

const Landing = () => {
    const classes = useStyles();
    const [checked, setChecked] = useState(false);
    // for animation of container
    useEffect(() => {
        setChecked(true);
    }, []);
    return (
        <StyledContainer id="landing">
            <Collapse
                in={checked}
                {...(checked ? {timeout: 1000} : {})}
                collapsedSize={50}
            >
                <div className={classes.container}>
                    <h1 className={classes.title}>
                        Welcome to <br/> Coin{" "}
                        <span className={classes.colorText}>Prices</span>
                    </h1>
                    <Scroll to="coin-price" smooth={true}>
                        <IconButton>
                            <ExpandMoreIcon className={classes.goDown}/>
                        </IconButton>
                    </Scroll>
                </div>
            </Collapse>
        </StyledContainer>
    )
}

export default Landing;