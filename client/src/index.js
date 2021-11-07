import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import {GlobalStoreContext, useGlobalStore} from "./store";
import '@fontsource/roboto'

const AppWrapper = () => {
    const store = useGlobalStore();
    return (
        // <ThemeProvider theme={theme}>
            <GlobalStoreContext.Provider value={store}>
                <App/>
            </GlobalStoreContext.Provider>
        // </ThemeProvider>
    )
}

ReactDOM.render(
    <React.StrictMode>
        <AppWrapper/>
    </React.StrictMode>,
    document.getElementById('root')
);

