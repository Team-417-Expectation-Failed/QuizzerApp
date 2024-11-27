import { Box, Typography } from "@mui/material";
import { getCategories } from "../quizapi";
import { useState, useEffect } from "react";
import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";

function CategoriesList() {

  const [categories, setCategories] = useState([]);

  useEffect(() => {
    getCategories()
      .then((data) => {
        setCategories(data);
      })
  }, []);

  return (
    <Box sx={{ margin: 5 }}>
      <Typography variant="h4">Categories</Typography>
      <TableContainer component={Paper} sx={{ marginTop: 2 }}>
        <Table aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Name</TableCell>
              <TableCell variant="head" sx={{ fontWeight: 'bold' }}>Description</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {categories.map((category) => (
              <TableRow key={category.id}>
                <TableCell variant="body"><RouterLink to={`#`}>{category.name}</RouterLink></TableCell>
                <TableCell variant="body">{category.description}</TableCell>
              </TableRow>
            ))}

          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}

export default CategoriesList;