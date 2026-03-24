/**
 * CampConnect - Camping Platform
 * Template based on Bootstrap v5.3.8
 * Updated: Jan 13 2026
 * License: https://bootstrapmade.com/license/
 *
 * ✅ MODIFIED FOR ANGULAR - Added null checks to prevent errors
 */

(function () {
    "use strict";

    /**
     * Apply .scrolled class to the body as the page is scrolled down
     * ✅ Added null checks for Angular compatibility
     */
    function toggleScrolled() {
        const selectBody = document.querySelector('body');
        const selectHeader = document.querySelector('#header');

        // ✅ Vérification null pour selectHeader
        if (!selectHeader || !selectBody) return;
        if (!selectHeader.classList.contains('scroll-up-sticky') &&
            !selectHeader.classList.contains('sticky-top') &&
            !selectHeader.classList.contains('fixed-top')) return;

        window.scrollY > 100 ? selectBody.classList.add('scrolled') : selectBody.classList.remove('scrolled');
    }

    // ✅ Vérifier que selectHeader existe avant d'ajouter les event listeners
    if (document.querySelector('#header')) {
        document.addEventListener('scroll', toggleScrolled);
        window.addEventListener('load', toggleScrolled);
    }

    /**
     * Mobile nav toggle
     * ✅ Added null checks
     */
    const mobileNavToggleBtn = document.querySelector('.mobile-nav-toggle');

    function mobileNavToogle() {
        const body = document.querySelector('body');
        if (!body || !mobileNavToggleBtn) return;

        body.classList.toggle('mobile-nav-active');
        mobileNavToggleBtn.classList.toggle('bi-list');
        mobileNavToggleBtn.classList.toggle('bi-x');
    }

    if (mobileNavToggleBtn && !mobileNavToggleBtn.dataset.bound) {
        mobileNavToggleBtn.dataset.bound = 'true';
        mobileNavToggleBtn.addEventListener('click', mobileNavToogle);
    }

    /**
     * Hide mobile nav on same-page/hash links
     * ✅ Added null checks
     */
    const navmenuLinks = document.querySelectorAll('#navmenu a');
    navmenuLinks.forEach(navmenu => {
        if (!navmenu.dataset.bound) {
            navmenu.dataset.bound = 'true';
            navmenu.addEventListener('click', () => {
                if (document.querySelector('.mobile-nav-active')) {
                    mobileNavToogle();
                }
            });
        }
    });

    /**
     * Toggle mobile nav dropdowns
     * ✅ Added null checks
     */
    const navmenuDropdowns = document.querySelectorAll('.navmenu .toggle-dropdown');
    navmenuDropdowns.forEach(navmenu => {
        if (!navmenu.dataset.bound) {
            navmenu.dataset.bound = 'true';
            navmenu.addEventListener('click', function (e) {
                e.preventDefault();
                if (this.parentNode) {
                    this.parentNode.classList.toggle('active');
                    if (this.parentNode.nextElementSibling) {
                        this.parentNode.nextElementSibling.classList.toggle('dropdown-active');
                    }
                }
                e.stopImmediatePropagation();
            });
        }
    });

    /**
     * Preloader disabled in SPA (prevent reload loops)
     */
    // const preloader = document.querySelector('#preloader');
    // if (preloader) {
    //   if (!preloader.dataset.bound) {
    //     preloader.dataset.bound = 'true';
    //     window.addEventListener('load', () => {
    //       preloader.remove();
    //     }, { once: true });
    //     setTimeout(() => {
    //       preloader.remove();
    //     }, 2000);
    //   }
    // }

    /**
     * Scroll top button
     * ✅ Already has null checks, enhanced
     */
    let scrollTop = document.querySelector('.scroll-top');

    function toggleScrollTop() {
        if (!scrollTop) return;

        window.scrollY > 100 ? scrollTop.classList.add('active') : scrollTop.classList.remove('active');
    }

    if (scrollTop) {
        scrollTop.addEventListener('click', (e) => {
            e.preventDefault();
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
        });

        window.addEventListener('load', toggleScrollTop);
        document.addEventListener('scroll', toggleScrollTop);
    }

    /**
     * Animation on scroll function and init
     * ✅ Already has null checks
     */
    function aosInit() {
        if (typeof AOS === 'undefined') {
            return;
        }
        AOS.init({
            duration: 600,
            easing: 'ease-in-out',
            once: true,
            mirror: false
        });
    }

    window.addEventListener('load', aosInit);

    /**
     * Initiate glightbox
     * ✅ Already has null checks
     */
    if (typeof GLightbox !== 'undefined') {
        GLightbox({
            selector: '.glightbox'
        });
    }

    /**
     * Init isotope layout and filters
     * ✅ Added enhanced null checks
     */
    document.querySelectorAll('.isotope-layout').forEach(function (isotopeItem) {
        if (typeof imagesLoaded === 'undefined' || typeof Isotope === 'undefined') {
            return;
        }

        let layout = isotopeItem.getAttribute('data-layout') ?? 'masonry';
        let filter = isotopeItem.getAttribute('data-default-filter') ?? '*';
        let sort = isotopeItem.getAttribute('data-sort') ?? 'original-order';

        let initIsotope;
        const isotopeContainer = isotopeItem.querySelector('.isotope-container');

        if (!isotopeContainer) return;

        imagesLoaded(isotopeContainer, function () {
            initIsotope = new Isotope(isotopeContainer, {
                itemSelector: '.isotope-item',
                layoutMode: layout,
                filter: filter,
                sortBy: sort
            });
        });

        const filterItems = isotopeItem.querySelectorAll('.isotope-filters li');
        filterItems.forEach(function (filters) {
            filters.addEventListener('click', function () {
                const activeFilter = isotopeItem.querySelector('.isotope-filters .filter-active');
                if (activeFilter) {
                    activeFilter.classList.remove('filter-active');
                }
                this.classList.add('filter-active');

                if (initIsotope) {
                    initIsotope.arrange({
                        filter: this.getAttribute('data-filter')
                    });
                }

                if (typeof aosInit === 'function') {
                    aosInit();
                }
            }, false);
        });
    });

    /**
     * Initiate Pure Counter
     * ✅ Already has null checks
     */
    if (typeof PureCounter !== 'undefined') {
        new PureCounter();
    }

    /**
     * Init swiper sliders
     * ✅ Added enhanced null checks
     */
    function initSwiper() {
        if (typeof Swiper === 'undefined') {
            return;
        }

        const swiperElements = document.querySelectorAll(".init-swiper");
        swiperElements.forEach(function (swiperElement) {
            const configElement = swiperElement.querySelector(".swiper-config");
            if (!configElement) return;

            let config = JSON.parse(configElement.innerHTML.trim());

            if (swiperElement.classList.contains("swiper-tab")) {
                // initSwiperWithCustomPagination(swiperElement, config);
            } else {
                new Swiper(swiperElement, config);
            }
        });
    }

    window.addEventListener("load", initSwiper);

    /**
     * Correct scrolling position upon page load for URLs containing hash links.
     * ✅ Added enhanced null checks
     */
    window.addEventListener('load', function (e) {
        if (window.location.hash) {
            const targetElement = document.querySelector(window.location.hash);
            if (targetElement) {
                setTimeout(() => {
                    let section = targetElement;
                    let scrollMarginTop = getComputedStyle(section).scrollMarginTop;
                    window.scrollTo({
                        top: section.offsetTop - parseInt(scrollMarginTop),
                        behavior: 'smooth'
                    });
                }, 100);
            }
        }
    });

    /**
     * Navmenu Scrollspy
     * ✅ Added enhanced null checks
     */
    let navmenulinks = document.querySelectorAll('.navmenu a');

    function navmenuScrollspy() {
        navmenulinks.forEach(navmenulink => {
            if (!navmenulink.hash) return;
            let section = document.querySelector(navmenulink.hash);
            if (!section) return;

            let position = window.scrollY + 200;
            if (position >= section.offsetTop && position <= (section.offsetTop + section.offsetHeight)) {
                const activeLinks = document.querySelectorAll('.navmenu a.active');
                activeLinks.forEach(link => link.classList.remove('active'));
                navmenulink.classList.add('active');
            } else {
                navmenulink.classList.remove('active');
            }
        });
    }

    // ✅ Only add scrollspy if navmenu exists
    if (navmenulinks.length > 0) {
        window.addEventListener('load', navmenuScrollspy);
        document.addEventListener('scroll', navmenuScrollspy);
    }

})();