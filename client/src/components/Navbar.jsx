import {AppBar, Toolbar, Typography, IconButton} from "@mui/material";
import {makeStyles} from '@mui/styles';
import SortIcon from '@mui/icons-material/Sort';

// const useStyles = makeStyles((theme) => ({
//     toolbar: {
//         display: "flex",
//         justifyContent : "space-between"
//     },
//     logoLg: {
//       display: "none",
//       [theme.breakpoints.up("sm")] : {
//           display: "block"
//       }
//     },
//     logoSm: {
//       display: "block",
//       [theme.breakpoints.up("sm")] : {
//           display: "none"
//       }
//     }
// }));
const useStyles = makeStyles({
    appbar: {
        background: ["none", "!important"],
    },
    appbarTitle: {
        flexGrow: "1",
        fontFamily: "Nunito",
    },
    appbarWrapper: {
        width: "80%",
        margin: "0 auto",
    },
    icon: {
        color: "#fff",
        fontSize: ["2rem", "!important"],
    },
    colorText: {
        color: "#5AFF3D",
    },
});

const Navbar = () => {
    const classes = useStyles();
    return (
        <AppBar className={classes.appbar} elevation={0}>
            <Toolbar className={classes.appbarWrapper}>
                <h1 className={classes.appbarTitle}>
                    Coin <span className={classes.colorText}>Prices</span>
                </h1>
                <IconButton disabled>
                    <SortIcon className={classes.icon} />
                </IconButton>
            </Toolbar>
        </AppBar>
    );
}

export default Navbar;