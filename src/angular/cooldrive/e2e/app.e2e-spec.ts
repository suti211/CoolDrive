import { CooldrivePage } from './app.po';

describe('cooldrive App', () => {
  let page: CooldrivePage;

  beforeEach(() => {
    page = new CooldrivePage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
