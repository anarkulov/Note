//
//  NoteItem.swift
//  iosApp
//
//  Created by eanarkulov on 12/22/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteItem: View {
    var note: Note
    var onDeleteClick: () -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(note.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeleteClick) {
                    Image(systemName: "xmark")
                        .foregroundColor(.black)
                }
            }.padding(.bottom, 4)
            
            Text(note.content)
                .fontWeight(.light)
                .padding(.bottom, 4)
            
            Text(DateTimeUtil().formatNoteDate(dateTime: note.createdAt))
                .font(.footnote)
                .frame(minWidth: 0, maxWidth: .infinity, alignment: Alignment.trailing)
        }
        .padding()
        .background(Color(hex: note.colorHex))
        .clipShape(RoundedRectangle(cornerRadius: 12.0))
    }
}

struct NoteItem_Previews: PreviewProvider {
    static var previews: some View {
        NoteItem(
            note: Note(id: nil, title: "Test", content: "Content", colorHex: 0xFF2321, createdAt: DateTimeUtil().getCurrentDateTime()),
            onDeleteClick: {}
        )
    }
}
