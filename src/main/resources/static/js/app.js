/**
 * ALGOVISION - CORE APPLICATION CONTROLLER
 * Handles topic loading, backend REST API communication, theory rendering, and theme toggling.
 */

let CURRENT_TOPIC = null;

document.addEventListener('DOMContentLoaded', () => {
  initTheme();

  // If on topic page, parse topic ID from URL
  const pathParts = window.location.pathname.split('/');
  if (pathParts.includes('topic') && pathParts.length >= 3) {
    const topicId = pathParts[pathParts.indexOf('topic') + 1];
    loadTopicPage(topicId);
  }
});

// ==========================================
// THEME MANAGEMENT (Light / Dark Mode)
// ==========================================
function initTheme() {
  const savedTheme = localStorage.getItem('algovision_theme') || 'light';
  document.documentElement.setAttribute('data-theme', savedTheme);
  updateThemeIcon(savedTheme);
}

function toggleTheme() {
  const current = document.documentElement.getAttribute('data-theme') || 'light';
  const newTheme = current === 'dark' ? 'light' : 'dark';
  document.documentElement.setAttribute('data-theme', newTheme);
  localStorage.setItem('algovision_theme', newTheme);
  updateThemeIcon(newTheme);
}

function updateThemeIcon(theme) {
  const icon = document.getElementById('theme-toggle-icon');
  if (icon) {
    icon.className = theme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
  }
}

function toggleSidebar() {
  const sidebar = document.getElementById('sidebar');
  if (sidebar) sidebar.classList.toggle('mobile-open');
}

// ==========================================
// TOPIC PAGE SETUP
// ==========================================
function loadTopicPage(topicId) {
  const topic = TOPIC_DATABASE[topicId];
  if (!topic) {
    console.error('Topic not found in database:', topicId);
    return;
  }
  CURRENT_TOPIC = topic;

  // Record exploration in ProgressTracker
  ProgressTracker.recordRecent(topic.id, topic.title, topic.category);

  // Update Navigation Active State
  const activeNav = document.getElementById(`nav-${topic.id}`);
  if (activeNav) activeNav.classList.add('active');

  // Breadcrumbs & Headers
  document.getElementById('breadcrumb-category').textContent = topic.category;
  document.getElementById('breadcrumb-title').textContent = topic.title;
  document.getElementById('topic-category-badge').textContent = topic.category;
  document.getElementById('topic-difficulty-badge').textContent = topic.difficulty;
  document.getElementById('topic-display-title').textContent = topic.title;
  document.getElementById('topic-display-intro').textContent = topic.intro;

  // Setup Visualizer & Input Controls
  setupVisualizerControls(topic);

  // Render Theory Section
  renderTheorySection(topic);

  // Update Mark Complete Button
  updateCompleteButtonState(topic.id);

  // Automatically run first visualization with default data
  startVisualization();
}

function setupVisualizerControls(topic) {
  const commonRow = document.getElementById('common-input-row');
  const dsRow = document.getElementById('ds-operations-row');
  const arrayInput = document.getElementById('custom-array-input');
  const targetInput = document.getElementById('target-input');

  // Populate default array
  if (arrayInput && topic.defaultData) {
    arrayInput.value = topic.defaultData.join(', ');
  }

  // Searching algorithm needs Target input
  if (topic.category === 'Searching Algorithms') {
    targetInput.style.display = 'block';
    targetInput.value = topic.defaultTarget || (topic.defaultData ? topic.defaultData[0] : 10);
  } else {
    targetInput.style.display = 'none';
  }

  // Data structure specific buttons (Push, Pop, Peek, Enqueue, Dequeue, Insert, Delete)
  if (topic.category === 'Data Structures' && topic.dsOperations) {
    dsRow.style.display = 'flex';
    const btnGroup = document.getElementById('ds-button-group');
    btnGroup.innerHTML = '';

    topic.dsOperations.forEach(op => {
      const btn = document.createElement('button');
      btn.className = 'btn-secondary';
      btn.style.padding = '0.5rem 0.9rem';
      btn.style.fontSize = '0.85rem';
      btn.innerHTML = `<i class="fas fa-play"></i> ${op.toUpperCase()}`;
      btn.onclick = () => runDataStructureOperation(op);
      btnGroup.appendChild(btn);
    });

    const posInput = document.getElementById('ds-pos-input');
    if (topic.id === 'arrays' || topic.id === 'linked-list') {
      posInput.style.display = 'block';
    } else {
      posInput.style.display = 'none';
    }
  } else {
    dsRow.style.display = 'none';
  }
}

// ==========================================
// BACKEND REST API COMMUNICATION
// ==========================================
async function startVisualization() {
  if (!CURRENT_TOPIC) return;

  const arrayInput = document.getElementById('custom-array-input');
  const rawText = arrayInput.value.trim();
  const arr = rawText.split(',').map(s => parseInt(s.trim())).filter(n => !isNaN(n));

  if (arr.length === 0) {
    alert('Please enter at least one valid number separated by commas.');
    return;
  }

  if (arr.length > 20) {
    alert('Please enter at most 20 elements for clear visualization.');
    return;
  }

  let endpoint = '';
  let payload = {};

  if (CURRENT_TOPIC.category === 'Sorting Algorithms') {
    endpoint = '/api/sort';
    payload = {
      algorithm: CURRENT_TOPIC.apiName,
      array: arr
    };
  } else if (CURRENT_TOPIC.category === 'Searching Algorithms') {
    const targetVal = parseInt(document.getElementById('target-input').value) || 0;
    endpoint = '/api/search';
    payload = {
      algorithm: CURRENT_TOPIC.apiName,
      array: arr,
      target: targetVal
    };
  } else if (CURRENT_TOPIC.category === 'Data Structures') {
    // Default initial visualization of data structure
    const initialStep = {
      stepNumber: 1,
      operation: 'INITIAL',
      array: arr,
      explanation: `Loaded ${CURRENT_TOPIC.title} with initial elements: [${arr.join(', ')}].`,
      pointers: CURRENT_TOPIC.id === 'stack' ? { top: arr.length - 1 } : (CURRENT_TOPIC.id === 'queue' ? { front: 0, rear: arr.length - 1 } : {})
    };
    AnimationController.loadSteps([initialStep], CURRENT_TOPIC.vizType);
    return;
  }

  try {
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    const data = await response.json();

    if (!response.ok || !data.success) {
      alert(data.error || 'Algorithm execution error from backend.');
      return;
    }

    AnimationController.loadSteps(data.steps, CURRENT_TOPIC.vizType);
    AnimationController.play();
  } catch (err) {
    console.error('API call failed:', err);
    alert('Failed to connect to Java backend. Please verify Spring Boot is running.');
  }
}

async function runDataStructureOperation(op) {
  if (!CURRENT_TOPIC) return;

  const arrayInput = document.getElementById('custom-array-input');
  const arr = arrayInput.value.split(',').map(s => parseInt(s.trim())).filter(n => !isNaN(n));

  const valInput = document.getElementById('ds-value-input');
  const val = valInput.value ? parseInt(valInput.value) : 55;

  const posInput = document.getElementById('ds-pos-input');
  const pos = posInput && posInput.value ? parseInt(posInput.value) : arr.length;

  try {
    const response = await fetch('/api/datastructure', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        algorithm: CURRENT_TOPIC.id,
        operation: op,
        array: arr,
        value: val,
        position: pos
      })
    });

    const data = await response.json();

    if (!response.ok || !data.success) {
      alert(data.error || 'Data structure operation failed.');
      return;
    }

    AnimationController.loadSteps(data.steps, CURRENT_TOPIC.vizType);
    AnimationController.play();

    // If final step modified array, update custom-array-input
    const lastStep = data.steps[data.steps.length - 1];
    if (lastStep && lastStep.array) {
      arrayInput.value = lastStep.array.join(', ');
    }
  } catch (err) {
    console.error('Data structure API error:', err);
    alert('Error running data structure operation.');
  }
}

function generateRandomData() {
  const count = 7;
  const arr = [];
  for (let i = 0; i < count; i++) {
    arr.push(Math.floor(Math.random() * 85) + 10);
  }

  // For binary search, sort it
  if (CURRENT_TOPIC && CURRENT_TOPIC.id === 'binary-search') {
    arr.sort((a, b) => a - b);
    const targetInput = document.getElementById('target-input');
    if (targetInput) targetInput.value = arr[Math.floor(Math.random() * arr.length)];
  }

  document.getElementById('custom-array-input').value = arr.join(', ');
  startVisualization();
}

// Playback controls bridge to AnimationController
function togglePlay() { AnimationController.togglePlay(); }
function nextStep() { AnimationController.nextStep(); }
function prevStep() { AnimationController.prevStep(); }
function restartVisualization() { AnimationController.restart(); }
function changeSpeed() {
  const sel = document.getElementById('speed-select');
  if (sel) AnimationController.setSpeed(sel.value);
}

// ==========================================
// WRITTEN THEORY RENDERING
// ==========================================
function renderTheorySection(topic) {
  const container = document.getElementById('theory-dynamic-content');
  if (!container || !topic.theory) return;

  const t = topic.theory;

  let compHtml = '';
  if (t.complexity) {
    compHtml = `
      <div class="theory-block">
        <h3><i class="fas fa-table" style="color: var(--muted-purple);"></i> Time & Space Complexity</h3>
        <div class="complexity-table-wrap">
          <table class="complexity-table">
            <thead>
              <tr>
                <th>Case / Metric</th>
                <th>Time / Space Complexity</th>
              </tr>
            </thead>
            <tbody>
              ${Object.entries(t.complexity).map(([key, val]) => `
                <tr>
                  <td style="text-transform: capitalize; font-weight: 600;">${key.replace('_', ' ')}</td>
                  <td><code>${val}</code></td>
                </tr>
              `).join('')}
            </tbody>
          </table>
        </div>
      </div>
    `;
  }

  let codeHtml = '';
  if (t.javaCode) {
    codeHtml = `
      <div class="theory-block">
        <h3><i class="fab fa-java" style="color: #E27D8C;"></i> Standard Java Implementation</h3>
        <div class="code-box">
          <pre><code>${escapeHtml(t.javaCode)}</code></pre>
        </div>
      </div>
    `;
  }

  let pseudoHtml = '';
  if (t.pseudocode) {
    pseudoHtml = `
      <div class="theory-block">
        <h3><i class="fas fa-code" style="color: var(--muted-purple);"></i> Algorithm Pseudocode</h3>
        <div class="code-box">
          <pre><code>${escapeHtml(t.pseudocode)}</code></pre>
        </div>
      </div>
    `;
  }

  container.innerHTML = `
    <div class="theory-block">
      <h3><i class="fas fa-circle-info" style="color: var(--muted-purple);"></i> Definition & Core Concept</h3>
      <p>${t.definition}</p>
    </div>

    <div class="theory-block">
      <h3><i class="fas fa-gears" style="color: var(--muted-purple);"></i> How It Works (Step-by-Step)</h3>
      <p>${t.howItWorks}</p>
    </div>

    ${compHtml}
    ${pseudoHtml}
    ${codeHtml}

    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem; margin-top: 1.5rem;">
      <div class="theory-block" style="background-color: var(--light-lavender); padding: 1.2rem; border-radius: 16px; border: 1px solid var(--border-color);">
        <h4 style="color: #2D8A61; margin-bottom: 0.6rem;"><i class="fas fa-thumbs-up"></i> Advantages</h4>
        <ul>
          ${(t.advantages || []).map(adv => `<li>${adv}</li>`).join('')}
        </ul>
      </div>

      <div class="theory-block" style="background-color: var(--blush-pink); padding: 1.2rem; border-radius: 16px; border: 1px solid var(--border-color);">
        <h4 style="color: #B83A4A; margin-bottom: 0.6rem;"><i class="fas fa-thumbs-down"></i> Disadvantages</h4>
        <ul>
          ${(t.disadvantages || []).map(dis => `<li>${dis}</li>`).join('')}
        </ul>
      </div>
    </div>

    <div class="theory-block" style="margin-top: 1.5rem;">
      <h3><i class="fas fa-lightbulb" style="color: #C2843A;"></i> Real-World Applications & Exam Significance</h3>
      <ul>
        ${(t.applications || []).map(app => `<li>${app}</li>`).join('')}
      </ul>
    </div>
  `;
}

function escapeHtml(text) {
  return text
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;");
}

// ==========================================
// COMPLETION & CELEBRATION (Lumi Mascot)
// ==========================================
function updateCompleteButtonState(topicId) {
  const btn = document.getElementById('mark-complete-btn');
  const btnText = document.getElementById('complete-btn-text');
  if (!btn || !btnText) return;

  const isCompleted = ProgressTracker.isCompleted(topicId);
  if (isCompleted) {
    btn.classList.add('completed');
    btnText.textContent = 'Completed ✓';
  } else {
    btn.classList.remove('completed');
    btnText.textContent = 'Mark as Completed';
  }
}

function toggleTopicCompletion() {
  if (!CURRENT_TOPIC) return;

  const isNowCompleted = ProgressTracker.toggleCompleted(CURRENT_TOPIC.id);
  updateCompleteButtonState(CURRENT_TOPIC.id);

  if (isNowCompleted) {
    // Show Lumi Celebration Modal!
    const overlay = document.getElementById('celebration-overlay');
    if (overlay) overlay.style.display = 'flex';
  }
}

function closeCelebration() {
  const overlay = document.getElementById('celebration-overlay');
  if (overlay) overlay.style.display = 'none';
}
