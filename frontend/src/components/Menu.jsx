import { useState } from 'react';
import { Box, AppBar, Toolbar, Typography } from '@mui/material';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import QuizList from './QuizList';
import CategoriesList from './CategoriesList';

function Menu() {

    const [value, setValue] = useState(0);

    const handleChange = (e, val) => {
        setValue(val);
    }

    return (
        <Box>
            <AppBar position='static'>
                <Toolbar>
                    <Typography variant='h6' sx={{ padding: 2 }}>Quizzer</Typography>
                    <Tabs value={value} onChange={handleChange} textColor='inherit'>
                        <Tab label='Quizzes' />
                        <Tab label='Categories' />
                    </Tabs>
                </Toolbar>
            </AppBar>

            {value === 0 && <QuizList />}
            {value === 1 && <CategoriesList />}

        </Box>
    );
}

export default Menu;