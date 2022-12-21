//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by eanarkulov on 12/21/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

extension NoteListScreen {
    @MainActor class NoteListViewModel: ObservableObject {
        private var noteDataSource: NoteDataSource? = nil
        
        private let searchNotes = SearchNote()
        
        private var notes = [Note]()
        @Published private(set) var filteredNotes = [Note]()
        @Published var searchText = "" {
            didSet {
                self.filteredNotes = searchNotes.execute(query: searchText, notes: self.notes)
            }
        }
        @Published private(set) var isSearchActive = false
        
        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNotes() {
            noteDataSource?.getAllNotes(completionHandler: { notes, error in
                self.notes = notes ?? []
                self.filteredNotes = self.notes
            })
        }
        
        func deleteNoteById(id: Int64?) {
            guard let id = id else {
                print("id id null")
                return
            }
            
            noteDataSource?.deleteNoteById(id: id, completionHandler: {error in
                self.loadNotes()
            })
        }
        
        func toggleIsSearchActive() {
            isSearchActive = !isSearchActive
            if !isSearchActive {
                searchText = ""
            }
        }
        
        func setNoteDataSource(noteDataSource: NoteDataSource) {
            self.noteDataSource = noteDataSource
        }
    }
}
