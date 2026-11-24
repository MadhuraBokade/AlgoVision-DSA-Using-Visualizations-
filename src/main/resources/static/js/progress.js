/**
 * ALGOVISION - LEARNING PROGRESS TRACKER
 * Uses browser localStorage to persist student learning progress without requiring a database.
 */

const STORAGE_KEYS = {
  COMPLETED: 'algovision_completed_topics',
  EXPLORED: 'algovision_explored_topics',
  RECENT: 'algovision_recent_topics',
  THEME: 'algovision_theme_mode'
};

const ALL_TOPICS_LIST = [
  { id: 'arrays', name: 'Arrays', category: 'Data Structures' },
  { id: 'linked-list', name: 'Singly Linked List', category: 'Data Structures' },
  { id: 'stack', name: 'Stack', category: 'Data Structures' },
  { id: 'queue', name: 'Queue', category: 'Data Structures' },
  { id: 'bubble-sort', name: 'Bubble Sort', category: 'Sorting Algorithms' },
  { id: 'selection-sort', name: 'Selection Sort', category: 'Sorting Algorithms' },
  { id: 'insertion-sort', name: 'Insertion Sort', category: 'Sorting Algorithms' },
  { id: 'merge-sort', name: 'Merge Sort', category: 'Sorting Algorithms' },
  { id: 'quick-sort', name: 'Quick Sort', category: 'Sorting Algorithms' },
  { id: 'linear-search', name: 'Linear Search', category: 'Searching Algorithms' },
  { id: 'binary-search', name: 'Binary Search', category: 'Searching Algorithms' }
];

const ProgressTracker = {
  getCompleted() {
    try {
      const data = localStorage.getItem(STORAGE_KEYS.COMPLETED);
      return data ? JSON.parse(data) : [];
    } catch (e) {
      return [];
    }
  },

  isCompleted(topicId) {
    return this.getCompleted().includes(topicId);
  },

  toggleCompleted(topicId) {
    const list = this.getCompleted();
    const idx = list.indexOf(topicId);
    let isNowCompleted = false;

    if (idx >= 0) {
      list.splice(idx, 1);
      isNowCompleted = false;
    } else {
      list.push(topicId);
      isNowCompleted = true;
    }

    localStorage.setItem(STORAGE_KEYS.COMPLETED, JSON.stringify(list));
    return isNowCompleted;
  },

  getExplored() {
    try {
      const data = localStorage.getItem(STORAGE_KEYS.EXPLORED);
      return data ? JSON.parse(data) : [];
    } catch (e) {
      return [];
    }
  },

  recordExplored(topicId) {
    const list = this.getExplored();
    if (!list.includes(topicId)) {
      list.push(topicId);
      localStorage.setItem(STORAGE_KEYS.EXPLORED, JSON.stringify(list));
    }
  },

  getRecent() {
    try {
      const data = localStorage.getItem(STORAGE_KEYS.RECENT);
      return data ? JSON.parse(data) : [];
    } catch (e) {
      return [];
    }
  },

  recordRecent(topicId, title, category) {
    let list = this.getRecent();
    // Remove if already in list to move to front
    list = list.filter(item => item.id !== topicId);
    list.unshift({
      id: topicId,
      title: title,
      category: category,
      timestamp: Date.now()
    });
    // Keep max 5 recent items
    if (list.length > 5) list = list.slice(0, 5);
    localStorage.setItem(STORAGE_KEYS.RECENT, JSON.stringify(list));
    this.recordExplored(topicId);
  },

  getStats() {
    const completed = this.getCompleted();
    const explored = this.getExplored();
    const total = ALL_TOPICS_LIST.length;

    // Count categories explored
    const exploredCats = new Set();
    explored.forEach(id => {
      const topic = ALL_TOPICS_LIST.find(t => t.id === id);
      if (topic) exploredCats.add(topic.category);
    });

    const percent = Math.round((completed.length / total) * 100);

    return {
      completedCount: completed.length,
      exploredCount: explored.length,
      totalTopics: total,
      categoriesCount: exploredCats.size,
      totalCategories: 3,
      percent: percent
    };
  },

  reset() {
    localStorage.removeItem(STORAGE_KEYS.COMPLETED);
    localStorage.removeItem(STORAGE_KEYS.EXPLORED);
    localStorage.removeItem(STORAGE_KEYS.RECENT);
  }
};
