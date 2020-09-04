class Dropzone extends EventTarget {

    constructor(el, opts = {}) {
        super()
        this.files = []
        this._elContainer = el
        this._elInput = this._createInputElement()
        this._addClickEventListener()
        this._addDropEventListener()
        this._proxyChangeEvent()

        this.url = opts.url

        this.addEventListener('change', this.upload.bind(this))
    }

    _hover(enable) {
        return () => {
            if (enable) {
                this._elContainer.classList.add('hover')
            } else {
                this._elContainer.classList.remove('hover')
            }
        }
    }

    _addClickEventListener() {
        this._elContainer.addEventListener('click', this.browseFiles.bind(this))
    }

    _addDropEventListener() {
        this._elContainer.addEventListener('dragenter', this._hover(true))
        this._elContainer.addEventListener('dragleave', this._hover(false))
        this._elContainer.addEventListener('dragover', e => e.preventDefault())
        this._elContainer.addEventListener('drop', e => {
            this.files = Array.from(e.dataTransfer.files)
            this.dispatchEvent(new Event('change'))
            e.preventDefault()
        })
    }

    _createInputElement() {
        const htmlInputElement = document.createElement('input')
        htmlInputElement.type = 'file'
        htmlInputElement.multiple = true
        return htmlInputElement;
    }

    _proxyChangeEvent() {
        this._elInput.addEventListener('change', e => {
            this.files = Array.from(e.target.files)
            this.dispatchEvent(new Event('change'))
        })
    }

    _upload(file) {
        const formData = new FormData()
        formData.append("file", file)
        formData.append("fileName", file.name)

        fetch(this.url, {
            method: 'POST',
            body: formData
        })
            .then(resp => resp.json())
            .then(json => ({ url: location.href + json.id, name: json.name }))
            .then(upload => {
                const el = new LinkComponent(upload).el
                const container = document.getElementById('links')
                container.appendChild(el)
            })
    }

    browseFiles() {
        this._elInput.click()
    }

    upload() {
        // disable hover effeckts
        this._hover(false)()

        // upload every file
        this.files.forEach(this._upload.bind(this))
    }

}

class LinkComponent {

    /**
     * @param {Object} opts
     * @param {string} opts.name
     * @param {string} opts.url
     */
    constructor(opts = {}) {
        this.el = this._template([
            new LabelComponent(opts),
            new UrlComponent(opts)
        ])
    }

    _template(children = []) {
        const el = document.createElement('div')
        el.classList.add('link')
        children.forEach(child => el.appendChild(child.el))
        return el
    }

}

class UrlComponent {

    /**
     * @param {Object} opts
     * @param {string} opts.url
     */
    constructor(opts = {}) {
        this.opts = opts
        this.children = [ new InputComponent(opts), new ButtonComponent() ]
        this.el = this._template()

        // add "copy to clipboard" functionality
        this.buttonComponent.el.addEventListener('click', this.inputComponent.copyToClipboard)
    }

    /**
     * @returns {HTMLDivElement}
     * @private
     */
    _template() {
        const el = document.createElement('div')
        el.classList.add('url')
        this.children.forEach(child => el.appendChild(child.el))
        return el;
    }

    /**
     * @returns {ButtonComponent}
     */
    get buttonComponent() {
        return this.children[1]
    }

    /**
     * @returns {InputComponent}
     */
    get inputComponent() {
        return this.children[0]
    }

}

class ButtonComponent {

    constructor() {
        this.el = this._template()
    }

    /**
     * @returns {HTMLInputElement}
     * @private
     */
    _template() {
        const el = document.createElement('input')
        el.type = 'button'
        el.title = 'copy to clipboard'
        el.addEventListener('click', () => this.dispatchEvent(new Event('click')))
        return el
    }

}

class InputComponent {

    /**
     * @param {Object} opts
     * @param {string} opts.url
     */
    constructor(opts = {}) {
        this.opts = opts
        this.el = this._template(opts.url)

        // add event listener to select the input text
        this.el.addEventListener('focus', () => this.el.select())
    }

    /**
     * @returns {HTMLInputElement}
     * @private
     */
    _template() {
        const el = document.createElement('input')
        el.type = 'text'
        el.value = this.opts.url
        el.readOnly = true
        return el
    }

    /**
     * copies input text to clipboard
     */
    copyToClipboard() {
        this.el.select()
        this.el.setSelectionRange(0, 99999)
        document.execCommand('copy')
    }

}

class LabelComponent {

    /**
     * @param {Object} opts
     * @param {string} opts.name
     */
    constructor(opts = {}) {
        this.opts = opts
        this.el = this._template(opts.name)
    }

    /**
     * @returns {HTMLLabelElement}
     * @private
     */
    _template() {
        const el = document.createElement('label')
        el.textContent = this.opts.name
        return el;
    }

}

new Dropzone(document.getElementById('dropzone'), {
    url: '/files'
})


