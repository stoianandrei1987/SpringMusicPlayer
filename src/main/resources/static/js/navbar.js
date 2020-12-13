$(document).ready(function () {
  $('.lgi-link').hide();
})

var playerElems = ['#bar', '.controlsOuter', '#title'];

var uid;

function makeRequest() {

  var songList;
  const Http = new XMLHttpRequest();
  const url = 'getjsonarray';
  Http.open("GET", url, true);
  Http.send();

  Http.onloadend = (e) => {
    songList = JSON.parse(Http.responseText);

    for (var i = 0; i < songList.length; i++) {
      var song = {
        title: songList[i].artist + ' - ' + songList[i].name,
        file: [songList[i].url],
        format: ['mp3'],
        howl: null
      }
      songObjArray.push(song);
    }


    player = new Player(songObjArray);
    loadEverything();
    // if(sound == undefined) player.load();
    //   player = new Player(songObjArray);



  }
}

$('#add-playlist').click(function() {document.getElementById('modal-window').style.display = 'block';});

function loadNewPlaylist(playlistId) {

  if (player != null) {



    let songList;
    let req = new XMLHttpRequest();
    let url = 'listsongsfrompl?pid=' + playlistId;
    req.open("GET", url, true);
    req.send();
    waveform.innerHTML = '';
    list.innerHTML = '';

    if(sound!=null) {
    sound.stop();
    sound.unload(); }
    player.kill();





    req.onloadend = (e) => {

      // songList = null;
      songList = JSON.parse(req.responseText);
      songObjArray = [];

      for (var i = 0; i < songList.length; i++) {
        var song = {
          title: songList[i].artist + ' - ' + songList[i].name,
          file: [songList[i].url],
          format: ['mp3'],
          howl: null
        }
        songObjArray.push(song);
      }
      player.switchPlaylist(songObjArray);
      loadEverything();
      loadWaveAndStart();
      // player.playlist = songObjArray;
      // player.index = 0;
      // player = new Player(songObjArray);
      // loadEverything();
      //if(sound == undefined) player.load();
      //player.play();
    }





  }

}


$("#login-form").on("submit", function (e) {

  var dataString = $('#login-form').serialize();



  const Http = new XMLHttpRequest();
  const url = "login";
  Http.open("POST", url, true);
  Http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  Http.send(dataString);
  Http.onloadend = () => {


    if (Http.status == 200) {

      for (var i = 0; i < playerElems.length; i++) {

        $(playerElems[i]).show();

      }

      $('#centered-welcome-text').hide();
      $('.nlg-stuff').hide();
      $('.lgi-link').show();

      uid = JSON.parse(Http.responseText)['uid'];

      fillPlaylistDropdown(uid);
      makeRequest();

    }

    else alert('Error logging in');

  }
  e.preventDefault();
});



function fillPlaylistDropdown(uid_param) {

  console.log('filling playlist for uid : ' + uid_param);

  $.ajax({
    url: "/getplaylist?uid=" + uid_param, success: function (result) {

      for (var i = 0; i < result.length; i++) {
        let playlistLink = document.createElement('a');
        playlistLink.setAttribute('class', 'dropdown-item');
        playlistLink.setAttribute('href', '#');
        playlistLink.innerHTML = result[i]['name'];
        playlistLink.setAttribute('onclick', "loadPlaylist('" + result[i]['playlist_id'] + "')");

        $('.dropdown-playlists-names').append(playlistLink);
      }
    }
  });

}

function loadPlaylist(playlistId) {
  loadNewPlaylist(playlistId);
}


function hideLoggedInBtns() {
  $('.lgi-link').hide();
  $('.nlg-stuff').show();
}



