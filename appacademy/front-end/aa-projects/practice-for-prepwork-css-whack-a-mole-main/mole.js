let score = 0;
let molesLeft = 30;
let popupLength = 3000;
let hideTimeout;
let clickable = false;

function popUpRandomMole() {
  if (molesLeft <= 0) {
    document.querySelector('.sb__game-over').classList.remove('sb__game-over--hidden');
    return;
  }

  const moleHeads = document.querySelectorAll('.molehill__head');

  if (moleHeads.length === 0) {
    return;
  }
  const moleIndex = Math.floor(Math.random() * moleHeads.length);
  const moleHead = moleHeads[moleIndex];

  clickable = true;

  moleHead.classList.remove('molehill__head--hidden', 'molehill__head--whacked');

  molesLeft -= 1;
  document.querySelector('.sb__moles').innerHTML = molesLeft;

  hideTimeout = setTimeout(() => hideMole(moleHead), popupLength);
}

function hideMole(mole) {
  clickable = false;
  mole.classList.add('molehill__head--hidden');

  setTimeout(popUpRandomMole, 500);
}

window.addEventListener('DOMContentLoaded', () => {
  setTimeout(popUpRandomMole, 0);

  const moleHeads = document.querySelectorAll('.molehill__head');

  for (let moleHead of moleHeads) {
    moleHead.addEventListener('click', event => {
      if (!clickable) return;

      score += 1;
      document.querySelector('.sb__score').innerHTML = score;
      popupLength -= popupLength / 10;

      clearTimeout(hideTimeout);
      hideMole(event.target);

      event.target.classList.add('molehill__head--hidden');

      event.target.classList.add('molehill__head--whacked');
    });
  }
});
