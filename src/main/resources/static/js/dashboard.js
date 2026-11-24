/**
 * ALGOVISION - DASHBOARD CONTROLLER
 * Handles stats presentation, continue-learning lists, daily study quotes, and search dropdowns.
 */

const MOTIVATIONAL_QUOTES = [
  { quote: "First, solve the problem. Then, write the code.", author: "John Johnson" },
  { quote: "Simplicity is prerequisite for reliability.", author: "Edsger W. Dijkstra" },
  { quote: "An algorithm must be seen to be believed.", author: "Donald Knuth" },
  { quote: "Bad programmers worry about the code. Good programmers worry about data structures and their relationships.", author: "Linus Torvalds" },
  { quote: "Step by step, the longest march is completed.", author: "B.Tech IT Wisdom" }
];

document.addEventListener('DOMContentLoaded', () => {
  renderDashboardStats();
  renderContinueLearning();
  renderDailyQuote();
});

function renderDashboardStats() {
  const stats = ProgressTracker.getStats();

  const exploredEl = document.getElementById('stat-explored-count');
  if (exploredEl) exploredEl.textContent = stats.exploredCount;

  const completedEl = document.getElementById('stat-completed-count');
  if (completedEl) completedEl.textContent = stats.completedCount;

  const categoriesEl = document.getElementById('stat-categories-count');
  if (categoriesEl) categoriesEl.textContent = `${stats.categoriesCount} / 3`;

  const percentEl = document.getElementById('stat-overall-percent');
  if (percentEl) percentEl.textContent = `${stats.percent}%`;

  const pillEl = document.getElementById('overall-progress-pill');
  if (pillEl) pillEl.innerHTML = `<i class="fas fa-award"></i> ${stats.percent}% Complete`;
}

function renderContinueLearning() {
  const container = document.getElementById('continue-learning-list');
  if (!container) return;

  const recents = ProgressTracker.getRecent();
  if (!recents || recents.length === 0) {
    container.innerHTML = '<p style="font-size: 0.88rem; color: var(--text-muted); padding: 0.8rem 0;">No recently viewed topics yet. Click any topic above to start exploring!</p>';
    return;
  }

  let html = '';
  recents.forEach(item => {
    html += `
      <a href="/topic/${item.id}" class="recent-item">
        <span><i class="fas fa-book-bookmark" style="color: var(--muted-purple); margin-right: 8px;"></i>${item.title}</span>
        <span style="font-size: 0.76rem; color: var(--text-muted);">${item.category} <i class="fas fa-chevron-right" style="margin-left: 4px;"></i></span>
      </a>
    `;
  });

  container.innerHTML = html;
}

function renderDailyQuote() {
  const quoteEl = document.getElementById('daily-quote');
  const authorEl = document.getElementById('daily-author');
  if (!quoteEl || !authorEl) return;

  const idx = Math.floor(Math.random() * MOTIVATIONAL_QUOTES.length);
  const q = MOTIVATIONAL_QUOTES[idx];
  quoteEl.textContent = `"${q.quote}"`;
  authorEl.textContent = `— ${q.author}`;
}

function handleTopicSearch(event) {
  const query = event.target.value.toLowerCase().trim();
  const dropdown = document.getElementById('search-dropdown');
  if (!dropdown) return;

  if (!query) {
    dropdown.style.display = 'none';
    dropdown.innerHTML = '';
    return;
  }

  const matches = ALL_TOPICS_LIST.filter(t => 
    t.name.toLowerCase().includes(query) || t.category.toLowerCase().includes(query)
  );

  if (matches.length === 0) {
    dropdown.innerHTML = '<div style="padding: 0.8rem; font-size: 0.85rem; color: var(--text-muted);">No matching topics found</div>';
  } else {
    let html = '';
    matches.forEach(m => {
      html += `
        <a href="/topic/${m.id}" style="display: flex; justify-content: space-between; padding: 0.7rem 1rem; text-decoration: none; color: var(--text-main); border-bottom: 1px solid var(--border-color); font-size: 0.88rem;">
          <span style="font-weight: 600;">${m.name}</span>
          <span style="font-size: 0.78rem; color: var(--muted-purple);">${m.category}</span>
        </a>
      `;
    });
    dropdown.innerHTML = html;
  }

  dropdown.style.display = 'block';
}

function resetProgress() {
  if (confirm('Are you sure you want to reset your study progress?')) {
    ProgressTracker.reset();
    renderDashboardStats();
    renderContinueLearning();
  }
}

function scrollToSection(sectionId) {
  const el = document.getElementById(sectionId);
  if (el) el.scrollIntoView({ behavior: 'smooth' });
}
