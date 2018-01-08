import { TestBed, inject } from '@angular/core/testing';

import { TacheRepositoryService } from './tache-repository.service';

describe('TacheRepositoryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TacheRepositoryService]
    });
  });

  it('should be created', inject([TacheRepositoryService], (service: TacheRepositoryService) => {
    expect(service).toBeTruthy();
  }));
});
