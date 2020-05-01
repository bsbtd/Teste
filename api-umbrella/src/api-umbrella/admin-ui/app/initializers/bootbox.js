import bootbox from 'bootbox';

export function initialize() {
  bootbox.setDefaults({
    animate: false,
  });
}

export default {
  name: 'bootbox',
  initialize,
};
