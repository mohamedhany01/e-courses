// Phase 2
const {
  getAllArtists,
  getLatestArtist,
  getArtistByArtistId,
  addArtist,
  editArtistByArtistId,
  deleteArtistByArtistId,
  getAlbumsForLatestArtist,
  getAlbumsByArtistId,
  getAlbumByAlbumId,
  addAlbumByArtistId,
  editAlbumByAlbumId,
  deleteAlbumByAlbumId,
  getFilteredAlbums,
  getSongsByArtistId,
  getSongsByAlbumId,
  getSongBySongId,
  addSongByAlbumId,
  editSongBySongId,
  deleteSongBySongId
} = require('./data');

const express = require('express');
const app = express();

app.use(express.json());
app.use((req, res, next) => {
  console.log('Request Body:', req.body);
  next();
});

app.get("/artists", (req, res) => {

  const results = getAllArtists();

  return res.json(results);
});

app.post("/artists", (req, res) => {

  const { name } = req.body;

  const artist = {
    name: name
  }

  const results = addArtist(artist);

  res.status(201);

  return res.json(results);
});

app.get("/artists/latest", (req, res) => {

  const results = getLatestArtist();

  res.status(200);

  return res.json(results);
});

app.get("/artists/latest/albums", (req, res) => {

  const results = getAlbumsForLatestArtist();

  res.status(200);

  return res.json(results);
});

app.get("/artists/:artistId", (req, res) => {

  const { artistId } = req.params;

  const results = getArtistByArtistId(artistId)

  res.status(200);

  return res.json(results);
});

app.put("/artists/:artistId", (req, res) => {

  const { artistId } = req.params;
  const { name } = req.body;

  const results = editArtistByArtistId(artistId, { name: name })

  res.status(200);

  return res.json(results);
});

app.patch("/artists/:artistId", (req, res) => {

  const { artistId } = req.params;
  const { name } = req.body;

  const results = editArtistByArtistId(artistId, { name: name })

  res.status(200);

  return res.json(results);
});

app.delete("/artists/:artistId", (req, res) => {

  const { artistId } = req.params;

  const results = deleteArtistByArtistId(parseInt(artistId, 10))

  res.status(200);

  return res.json({
    "message": "Successfully deleted"
  });
});

app.get("/artists/:artistId/albums", (req, res) => {

  const { artistId } = req.params;

  const results = getAlbumsByArtistId(artistId)

  res.status(200);

  return res.json(results);
});

app.get("/albums/:albumId", (req, res) => {

  const { albumId } = req.params;

  const results = getAlbumByAlbumId(albumId)

  res.status(200);

  return res.json(results);
});

app.post("/artists/:artistId/albums", (req, res) => {

  const { name } = req.body;
  const { artistId } = req.params;

  const results = addAlbumByArtistId(artistId, { name: name })

  res.status(201);

  return res.json(results);
});


app.patch("/albums/:albumId", (req, res) => {

  const { albumId } = req.params;
  const { name } = req.body;

  const results = editAlbumByAlbumId(albumId, { name: name })

  res.status(200);

  return res.json(results);
});

app.put("/albums/:albumId", (req, res) => {

  const { albumId } = req.params;
  const { name } = req.body;

  const results = editAlbumByAlbumId(albumId, { name: name })

  res.status(200);

  return res.json(results);
});

app.delete("/albums/:albumId", (req, res) => {
  const { albumId } = req.params;

  deleteAlbumByAlbumId(albumId);

  res.status(200);

  return res.json({
    "message": "Successfully deleted"
  });

});

app.get("/albums", (req, res) => {

  const { startsWith } = req.query;

  const results = getFilteredAlbums(startsWith);

  res.status(200);

  return res.json(results);
});

app.get("/songs/:songId", (req, res) => {

  const { songId } = req.params;

  const results = getSongBySongId(songId);

  res.status(200);

  return res.json(results);
});

app.post("/albums/:albumId/songs", (req, res) => {

  const { albumId } = req.params;
  const data = req.body;

  const results = addSongByAlbumId(albumId, data);

  res.status(201);

  return res.json(results);
});

app.get("/artists/:artistId/songs", (req, res) => {

  const { artistId } = req.params;

  const results = getSongsByArtistId(artistId);

  res.status(200);

  return res.json(results);
});

app.get("/albums/:albumId/songs", (req, res) => {

  const { albumId } = req.params;

  const results = getSongsByAlbumId(albumId);

  res.status(200);

  return res.json(results);
});

app.put("/songs/:songId", (req, res) => {

  const { songId } = req.params;
  const data = req.body;

  const results = editSongBySongId(songId, data);

  res.status(200);

  return res.json(results);
});

app.patch("/songs/:songId", (req, res) => {

  const { songId } = req.params;
  const data = req.body;

  const results = editSongBySongId(songId, data);

  res.status(200);

  return res.json(results);
});

app.delete("/songs/:songId", (req, res) => {
  const { songId } = req.params;

  deleteSongBySongId(songId);

  res.status(200);

  return res.json({
    "message": "Successfully deleted"
  });

});


// DO NOT MODIFY
if (require.main === module) {
  const port = 8000;
  app.listen(port, () => console.log('Server is listening on port', port));
} else {
  module.exports = app;
}
