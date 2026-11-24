/**
 * ALGOVISION - ANIMATION CONTROLLER
 * Controls playback lifecycle: play, pause, next step, previous step, restart, speed adjustment.
 */

const AnimationController = {
  steps: [],
  currentIndex: 0,
  isPlaying: false,
  timer: null,
  speed: 900,
  vizType: 'bars',

  loadSteps(steps, vizType) {
    this.pause();
    this.steps = steps || [];
    this.currentIndex = 0;
    this.vizType = vizType || 'bars';
    this.updateUI();

    if (this.steps.length > 0) {
      VisualizationEngine.render(this.steps[0], this.vizType);
    }
  },

  play() {
    if (this.steps.length === 0) return;
    if (this.currentIndex >= this.steps.length - 1) {
      this.currentIndex = 0;
    }

    this.isPlaying = true;
    this.updatePlayBtn(true);

    const stepRunner = () => {
      if (!this.isPlaying) return;
      if (this.currentIndex < this.steps.length - 1) {
        this.currentIndex++;
        this.updateUI();
        VisualizationEngine.render(this.steps[this.currentIndex], this.vizType);
        this.timer = setTimeout(stepRunner, this.speed);
      } else {
        this.pause();
      }
    };

    this.timer = setTimeout(stepRunner, this.speed);
  },

  pause() {
    this.isPlaying = false;
    if (this.timer) {
      clearTimeout(this.timer);
      this.timer = null;
    }
    this.updatePlayBtn(false);
  },

  togglePlay() {
    if (this.isPlaying) {
      this.pause();
    } else {
      this.play();
    }
  },

  nextStep() {
    this.pause();
    if (this.currentIndex < this.steps.length - 1) {
      this.currentIndex++;
      this.updateUI();
      VisualizationEngine.render(this.steps[this.currentIndex], this.vizType);
    }
  },

  prevStep() {
    this.pause();
    if (this.currentIndex > 0) {
      this.currentIndex--;
      this.updateUI();
      VisualizationEngine.render(this.steps[this.currentIndex], this.vizType);
    }
  },

  restart() {
    this.pause();
    this.currentIndex = 0;
    this.updateUI();
    if (this.steps.length > 0) {
      VisualizationEngine.render(this.steps[0], this.vizType);
    }
  },

  setSpeed(ms) {
    this.speed = parseInt(ms, 10);
  },

  updateUI() {
    const total = this.steps.length;
    const current = total > 0 ? this.currentIndex + 1 : 0;

    const indicator = document.getElementById('step-indicator');
    if (indicator) {
      indicator.textContent = `Step ${current} of ${total}`;
    }

    const fill = document.getElementById('viz-progress-fill');
    if (fill) {
      const pct = total > 1 ? Math.round((this.currentIndex / (total - 1)) * 100) : (total === 1 ? 100 : 0);
      fill.style.width = `${pct}%`;
    }
  },

  updatePlayBtn(isPlaying) {
    const icon = document.getElementById('play-pause-icon');
    if (icon) {
      icon.className = isPlaying ? 'fas fa-pause' : 'fas fa-play';
    }
  }
};
