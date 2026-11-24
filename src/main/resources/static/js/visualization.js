/**
 * ALGOVISION - VISUALIZATION ENGINE
 * Renders bars, array cells, stacks, queues, and linked lists according to backend execution states.
 */

const VisualizationEngine = {
  container: null,

  init(containerId) {
    this.container = document.getElementById(containerId);
  },

  render(step, vizType) {
    if (!this.container) this.container = document.getElementById('canvas-container');
    if (!this.container || !step) return;

    // Update explanations & badges
    this.updateOperationInfo(step);

    const arr = step.array || [];
    const active = step.activeIndices || [];
    const sorted = step.sortedIndices || [];
    const swapped = step.swappedIndices || [];
    const pointers = step.pointers || {};
    const extra = step.extra || '';

    switch (vizType) {
      case 'bars':
        this.renderBars(arr, active, sorted, swapped, pointers);
        break;
      case 'cells':
        this.renderCells(arr, active, sorted, swapped, pointers, extra);
        break;
      case 'stack':
        this.renderStack(arr, active, sorted, pointers, step.operation);
        break;
      case 'queue':
        this.renderQueue(arr, active, sorted, pointers);
        break;
      case 'linkedlist':
        this.renderLinkedList(arr, active, pointers);
        break;
      default:
        this.renderBars(arr, active, sorted, swapped, pointers);
    }
  },

  updateOperationInfo(step) {
    const badge = document.getElementById('operation-badge');
    const explanation = document.getElementById('operation-explanation');

    if (badge) {
      badge.textContent = step.operation || 'STEP';
      // Style badge based on operation
      badge.style.backgroundColor = this.getBadgeColor(step.operation);
    }

    if (explanation) {
      explanation.textContent = step.explanation || 'Processing algorithm step...';
    }
  },

  getBadgeColor(op) {
    if (!op) return 'var(--muted-purple)';
    const o = op.toUpperCase();
    if (o.includes('COMPARE') || o.includes('CHECK')) return 'var(--color-compare)';
    if (o.includes('SWAP') || o.includes('SHIFT') || o.includes('DELETE')) return 'var(--color-swap)';
    if (o.includes('SORTED') || o.includes('FOUND') || o.includes('COMPLETE')) return 'var(--color-sorted)';
    if (o.includes('PUSH') || o.includes('ENQUEUE') || o.includes('INSERT')) return 'var(--muted-purple)';
    if (o.includes('UNDERFLOW') || o.includes('OVERFLOW') || o.includes('ERROR')) return '#D9534F';
    return 'var(--muted-purple)';
  },

  renderBars(arr, active, sorted, swapped, pointers) {
    if (!arr || arr.length === 0) {
      this.container.innerHTML = '<p style="color:var(--text-muted)">Empty array</p>';
      return;
    }

    const maxVal = Math.max(...arr.map(Math.abs), 1);
    let html = '<div class="bars-canvas">';

    arr.forEach((val, idx) => {
      let stateClass = '';
      if (swapped.includes(idx)) stateClass = 'state-swap';
      else if (active.includes(idx)) stateClass = 'state-compare';
      else if (sorted.includes(idx)) stateClass = 'state-sorted';

      // Height scaled between 40px and 200px
      const heightPx = Math.max(38, Math.round((Math.abs(val) / maxVal) * 190));

      // Pointer label if present
      let pointerHtml = '';
      for (const [key, pIdx] of Object.entries(pointers)) {
        if (pIdx === idx) {
          pointerHtml += `<div class="bar-pointer">${key}</div>`;
        }
      }

      html += `
        <div class="viz-bar-wrapper">
          <span class="bar-val-top">${val}</span>
          <div class="viz-bar ${stateClass}" style="height: ${heightPx}px;"></div>
          <span class="bar-idx-bottom">${idx}</span>
          ${pointerHtml}
        </div>
      `;
    });

    html += '</div>';
    this.container.innerHTML = html;
  },

  renderCells(arr, active, sorted, swapped, pointers, extra) {
    if (!arr || arr.length === 0) {
      this.container.innerHTML = '<p style="color:var(--text-muted)">Empty array</p>';
      return;
    }

    // Check for eliminated range in binary search
    let elimStart = -1, elimEnd = -1;
    if (extra && extra.includes('eliminated:')) {
      const match = extra.match(/eliminated:(\d+)-(\d+)/);
      if (match) {
        elimStart = parseInt(match[1]);
        elimEnd = parseInt(match[2]);
      }
    }

    let html = '<div class="cells-canvas">';

    arr.forEach((val, idx) => {
      let stateClass = '';
      if (swapped.includes(idx)) stateClass = 'state-swap';
      else if (active.includes(idx)) stateClass = 'state-compare';
      else if (sorted.includes(idx)) stateClass = 'state-sorted';

      // Gray out eliminated segments
      if (elimStart !== -1 && idx >= elimStart && idx <= elimEnd) {
        stateClass += ' state-eliminated';
      }

      // Pointer tags
      let pointerTags = [];
      for (const [key, pIdx] of Object.entries(pointers)) {
        if (pIdx === idx) {
          pointerTags.push(key);
        }
      }

      let pointerHtml = '';
      if (pointerTags.length > 0) {
        pointerHtml = `<div class="cell-pointer-bottom">${pointerTags.join(', ')}</div>`;
      }

      html += `
        <div class="cell-wrapper">
          <span class="cell-index-top">[${idx}]</span>
          <div class="viz-cell ${stateClass}">
            ${val}
          </div>
          ${pointerHtml}
        </div>
      `;
    });

    html += '</div>';
    this.container.innerHTML = html;
  },

  renderStack(arr, active, sorted, pointers, op) {
    let html = '<div class="stack-canvas">';

    if (!arr || arr.length === 0) {
      html += '<div style="margin: auto; color: var(--text-muted); font-size: 0.9rem;">Stack is Empty (top = -1)</div>';
    } else {
      arr.forEach((val, idx) => {
        const isTop = (idx === arr.length - 1);
        const topClass = isTop ? 'top-item' : '';
        const activeClass = active.includes(idx) ? 'state-compare' : '';

        html += `
          <div class="stack-item ${topClass} ${activeClass}">
            <span>Index [${idx}]</span>
            <span style="font-size: 1.15rem; font-weight: 800;">${val}</span>
            <span style="font-size: 0.8rem; color: var(--muted-purple);">${isTop ? '◀ TOP' : ''}</span>
          </div>
        `;
      });
    }

    html += '</div>';
    this.container.innerHTML = html;
  },

  renderQueue(arr, active, sorted, pointers) {
    let html = '<div class="queue-canvas">';

    if (!arr || arr.length === 0) {
      html += '<div style="margin: auto; color: var(--text-muted); font-size: 0.9rem;">Queue is Empty</div>';
    } else {
      arr.forEach((val, idx) => {
        const isFront = (idx === 0);
        const isRear = (idx === arr.length - 1);
        const activeClass = active.includes(idx) ? 'state-compare' : '';

        let badgeText = '';
        if (isFront && isRear) badgeText = 'FRONT & REAR';
        else if (isFront) badgeText = 'FRONT';
        else if (isRear) badgeText = 'REAR';

        html += `
          <div class="queue-item ${activeClass}">
            <span style="font-size: 0.72rem; color: var(--text-muted);">[${idx}]</span>
            <span style="font-size: 1.1rem; font-weight: 800; color: var(--text-main);">${val}</span>
            <span style="position: absolute; bottom: -24px; font-size: 0.7rem; font-weight: 700; color: var(--muted-purple); white-space: nowrap;">${badgeText}</span>
          </div>
        `;
      });
    }

    html += '</div>';
    this.container.innerHTML = html;
  },

  renderLinkedList(arr, active, pointers) {
    let html = '<div class="linkedlist-canvas">';

    if (!arr || arr.length === 0) {
      html += '<div style="margin: auto; color: var(--text-muted); font-size: 0.9rem;">Head -> null (List is Empty)</div>';
    } else {
      arr.forEach((val, idx) => {
        const isHead = (idx === 0);
        const isActive = active.includes(idx);
        const stateStyle = isActive ? 'border-color: var(--color-compare); background-color: #FFECC8;' : '';

        html += `
          <div style="display: flex; align-items: center; gap: 8px;">
            <div class="ll-node" style="${stateStyle}">
              <div class="ll-data">
                ${isHead ? '<div style="font-size: 0.68rem; color: var(--muted-purple); font-weight: 700;">HEAD</div>' : ''}
                ${val}
              </div>
              <div class="ll-next">•</div>
            </div>
            <div class="ll-arrow">➔</div>
          </div>
        `;
      });

      html += '<div style="font-weight: 700; color: var(--text-muted); padding: 0.5rem;">null</div>';
    }

    html += '</div>';
    this.container.innerHTML = html;
  }
};
