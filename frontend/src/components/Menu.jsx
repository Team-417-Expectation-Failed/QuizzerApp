import { Box, AppBar, Toolbar, Typography } from '@mui/material';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { Link, Outlet, useLocation } from 'react-router-dom';

function Menu() {
    const location = useLocation();

    // determine which tab is active based on the current path
    const getTabValue = () => {
        if (location.pathname.startsWith('/quiz')) return 0;
        if (location.pathname.startsWith('/categories')) return 1;
        return false; // Nothing selected
    };

    return (
        <Box>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" sx={{ padding: 2 }}>Quizzer</Typography>
                    <Tabs value={getTabValue()} textColor="inherit">
                        <Tab label="Quizzes" component={Link} to="/" />
                        <Tab label="Categories" component={Link} to="/categories" />
                    </Tabs>
                </Toolbar>
            </AppBar>

            {/* Outlet renders the active child route */}
            <Outlet />
        </Box>
    );
}

export default Menu;
